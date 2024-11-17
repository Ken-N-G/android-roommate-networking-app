package com.example.roomiesapplication.viewmodels

import android.text.format.DateUtils
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomiesapplication.model.ChatItemData
import com.example.roomiesapplication.viewmodels.states.ChatScreenState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Date

class ChatScreenViewModel: ViewModel() {

    private val _chatState = mutableStateOf(ChatScreenState())
    val chatState: State<ChatScreenState> = _chatState

    init {
        viewModelScope.launch {
            fetchChats()
        }
    }

    private suspend fun fetchChats() {
        val selfUID = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        val db = FirebaseFirestore.getInstance()
        try {
            val chatSnapshots = db.collection("users").document(selfUID).collection("chatIDs").get().await()
            chatSnapshots.forEach() { snapshot ->
                val otherUID = snapshot.getString("otherUID").orEmpty()
                val otherUserSnapshot = db.collection("users").document(otherUID).get().await()
                val username = otherUserSnapshot.getString("username").orEmpty()
                val profileURL = otherUserSnapshot.getString("profileURL").orEmpty()
                val chatID = snapshot.getString("chatID").orEmpty()
                val messageSnapshots = db.collection("chats").document(chatID).collection("messages").orderBy("dateSent").get().await()
                val lastMessageSent = messageSnapshots.last().getString("content").orEmpty()
                val dateLastMessageSent = messageSnapshots.last().getDate("dateSent") ?: Date()
                val chatItem = ChatItemData(
                    username = username,
                    profileURL = profileURL,
                    lastMessageSent = lastMessageSent,
                    dateLastMessageSent = dateLastMessageSent,
                    uid = otherUID
                )
                _chatState.value.ChatList.add(chatItem)
            }
        } catch (error: Exception) {
            _chatState.value.ChatErrorMsg = error.toString()
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
}