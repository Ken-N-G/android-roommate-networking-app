package com.example.roomiesapplication.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.roomiesapplication.model.ErrorMsgConstants
import com.example.roomiesapplication.model.FeedItem
import com.example.roomiesapplication.viewmodels.states.AddPostScreenState
import com.example.roomiesapplication.viewmodels.states.HomeScreenState
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Date

class AddPostScreenViewModel: ViewModel() {

    private val _addPostState = mutableStateOf(AddPostScreenState())
    val addPostState: State<AddPostScreenState> = _addPostState

    suspend fun AddPost(): DocumentReference? {
        if (!_addPostState.value.PostValid) {
            _addPostState.value.AddPostErrorMsg = ErrorMsgConstants.POST_IS_EMPTY
            null
        }
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid ?: ""
        return if (uid.isEmpty()) {
            null
        } else {
            try {
                val userDocSnapshot = db.collection("users").document(uid).get().await()
                val username = userDocSnapshot.getString("username").orEmpty()
                val profileURL = userDocSnapshot.getString("profileURL").orEmpty()
                if (username.isNotEmpty()) {
                    val FeedItem = FeedItem(
                        username = username,
                        profileURL = profileURL,
                        uid = uid,
                        datePosted = Date(),
                        postContent = _addPostState.value.PostContent
                    )
                    val docRef = db.collection("posts").add(FeedItem).await()
                    _addPostState.value.AddPostErrorMsg = ErrorMsgConstants.ON_POST_SUCCESS
                    docRef
                } else {
                    null
                }
            } catch (error: Exception) {
                _addPostState.value.AddPostErrorMsg = error.toString()
                null
            }
        }
    }

    fun OnPostContentFieldChange(change: String) {
        _addPostState.value = addPostState.value.copy(
            PostContent = change
        )
        validatePost()
    }

    private fun validatePost() {
        val isEmpty = checkIsEmpty(
            input = _addPostState.value.PostContent,
            onError = {
                _addPostState.value.PostContentErrorMsg = ErrorMsgConstants.POST_IS_EMPTY
            }
        )
        val isValid = !isEmpty
        _addPostState.value.PostValid = isValid
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
}