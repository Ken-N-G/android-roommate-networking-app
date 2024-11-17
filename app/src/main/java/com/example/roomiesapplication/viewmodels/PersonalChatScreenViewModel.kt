package com.example.roomiesapplication.viewmodels

import android.text.format.DateUtils
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomiesapplication.model.ErrorMsgConstants
import com.example.roomiesapplication.model.MessageItemData
import com.example.roomiesapplication.model.PersonalChatData
import com.example.roomiesapplication.viewmodels.states.PersonalChatScreenState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Date

class PersonalChatScreenViewModel(uid: String): ViewModel() {

    private val _personalChatState = mutableStateOf(PersonalChatScreenState())
    val personalChatState: State<PersonalChatScreenState> = _personalChatState

    init {
        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()
            _personalChatState.value.OtherUID = uid
            _personalChatState.value.SelfUID = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            val chatID = checkForChatList(db)
            if (chatID.isNotEmpty()) {
                fetchMessages(checkForChatList(db), db)
            } else {
            }
            fetchUsernames(_personalChatState.value.SelfUID, _personalChatState.value.OtherUID, db)
        }
    }

    fun OnRefresh() {
        val db = FirebaseFirestore.getInstance()
        viewModelScope.launch {
            fetchUsernames(_personalChatState.value.SelfUID, _personalChatState.value.OtherUID, db)
            fetchMessages(checkForChatList(db), db)
        }
    }

    fun formatDate(postDate: Date): String {
        val today = Date()
        if ((today.time - postDate.time) < DateUtils.MINUTE_IN_MILLIS) {
            return "Just Now"
        } else {
            return DateUtils.getRelativeTimeSpanString(
                postDate.time, today.time, DateUtils.MINUTE_IN_MILLIS
            ).toString()
        }
    }

    fun SendMessage() {
        if (!_personalChatState.value.MessageValid) {
            return
        }
        val db = FirebaseFirestore.getInstance()
        viewModelScope.launch {
            var chatID = checkForChatList(db)
            try {
                if (chatID.isNotEmpty()) {
                    val newMessage = MessageItemData(
                        sender = _personalChatState.value.SelfUID,
                        content = _personalChatState.value.MessageContent,
                        dateSent = Date()
                    )
                    db.collection("chats").document(chatID).collection("messages").add(newMessage).await()
                } else {
                    val newMessage = MessageItemData(
                        sender = _personalChatState.value.SelfUID,
                        content = _personalChatState.value.MessageContent,
                        dateSent = Date()
                    )
                    val docRef = db.collection("chats").document()
                    chatID = docRef.id
                    db.collection("chats").document(chatID).collection("messages").add(newMessage).await()
                    createChatEntry(chatID, db)
                }
                fetchMessages(checkForChatList(db), db)
            } catch (error: Exception) {

            }
        }
    }

    fun OnPostContentFieldChange(change: String) {
        _personalChatState.value = personalChatState.value.copy(
            MessageContent = change
        )
        validateMessage()
    }

    private suspend fun createChatEntry(chatID: String, db: FirebaseFirestore) {
        val PersonalChatEntryForOther = PersonalChatData(
            chatID = chatID,
            otherUID = _personalChatState.value.SelfUID
        )
        val PersonalChatEntryForSelf = PersonalChatData(
            chatID = chatID,
            otherUID = _personalChatState.value.OtherUID
        )
        try {
            db.collection("users").document(_personalChatState.value.OtherUID).collection("chatIDs").document(_personalChatState.value.SelfUID).set(PersonalChatEntryForOther).await()
            db.collection("users").document(_personalChatState.value.SelfUID).collection("chatIDs").document(_personalChatState.value.OtherUID).set(PersonalChatEntryForSelf).await()
        } catch (error: Exception) {
        }
    }

    private fun validateMessage() {
        val isEmpty = checkIsEmpty(
            input = _personalChatState.value.MessageContent,
            onError = {
                _personalChatState.value.PersonalChatErrorMsg = ErrorMsgConstants.POST_IS_EMPTY
            }
        )
        val isValid = !isEmpty
        _personalChatState.value.MessageValid = isValid
    }

    private fun checkIsEmpty(
        input: String,
        onError: () -> Unit
    ): Boolean {
        val isEmpty = input.isEmpty()
        if (isEmpty) {
            onError()
        }
        return isEmpty
    }

    private suspend fun checkForChatList(db: FirebaseFirestore): String {
        return try {
            val docSnapshot = db.collection("users").document(_personalChatState.value.OtherUID).collection("chatIDs").get().await()
            if (!docSnapshot.isEmpty) {
                var chatID = ""
                docSnapshot.forEach() { snapshot ->
                    val targetUID = snapshot.getString("otherUID").orEmpty()
                    if (targetUID == _personalChatState.value.SelfUID) {
                        val chatDocSnapshot = db.collection("users").document(_personalChatState.value.OtherUID)
                            .collection("chatIDs").document(targetUID).get().await()
                        chatID = chatDocSnapshot.getString("chatID").orEmpty()
                    }
                }
                chatID
            } else {
                ""
            }
        } catch (error: Exception) {
            ""
        }
    }

    private suspend fun fetchMessages(chatID: String, db: FirebaseFirestore) {
        try {
            _personalChatState.value.MessageList.clear()
            val messagesSnapshot = db.collection("chats").document(chatID).collection("messages").orderBy("dateSent").get().await()
            messagesSnapshot.forEach() { docSnapshot ->
                val sender = docSnapshot.getString("sender").orEmpty()
                val content = docSnapshot.getString("content").orEmpty()
                val dateSent = docSnapshot.getDate("dateSent") ?: Date()
                val message = MessageItemData(
                    sender = sender,
                    content = content,
                    dateSent = dateSent
                )
                _personalChatState.value.MessageList.add(message)
            }
        } catch (error: Exception) {

        }
    }

    private suspend fun fetchUsernames(userUID: String, uid: String ,db: FirebaseFirestore) {
        try {
            val userDocSnapshot = db.collection("users").document(userUID).get().await()
            val otherUserDocSnapshot = db.collection("users").document(uid).get().await()
            _personalChatState.value = _personalChatState.value.copy(
                SelfUsername = userDocSnapshot.getString("username").orEmpty(),
                OtherUsername = otherUserDocSnapshot.getString("username").orEmpty()
            )
        } catch (error: Exception) {

        }
    }
}