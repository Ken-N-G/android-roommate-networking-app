package com.example.roomiesapplication.viewmodels

import android.text.format.DateUtils
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomiesapplication.model.FeedItem
import com.example.roomiesapplication.viewmodels.states.HomeScreenState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Date

class HomeScreenViewModel: ViewModel() {

    private val _homeState = mutableStateOf(HomeScreenState())
    val homeState: State<HomeScreenState> = _homeState

    init {
        _homeState.value.FeedList.clear()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loadFeed()
            }
            _homeState.value.currentUID = fetchUserUID()
        }
    }

    private suspend fun loadFeed() {
        val db = FirebaseFirestore.getInstance()
        try {
            val snapshots = db.collection("posts").orderBy("datePosted", Query.Direction.DESCENDING).get().await()
            snapshots.forEach() { docSnapshot ->
                val feedItem = FeedItem(
                    profileURL = docSnapshot.getString("profileURL").orEmpty(),
                    username = docSnapshot.getString("username").orEmpty(),
                    datePosted = docSnapshot.getDate("datePosted") ?: Date(),
                    postContent = docSnapshot.getString("postContent").orEmpty(),
                    uid = docSnapshot.getString("uid").orEmpty()
                )
                _homeState.value.FeedList.add(feedItem)
            }
            _homeState.value.IsLoading = false
        } catch (error: Exception) {
            _homeState.value.HomeErrorMsg = error.toString()
        }
    }

    private fun fetchUserUID(): String {
        return FirebaseAuth.getInstance().currentUser?.uid ?: ""
    }

    fun isUIDSelfReferencing(uid: String): Boolean {
         return uid == _homeState.value.currentUID
    }

    fun onRefresh() {
        _homeState.value.FeedList.clear()
        viewModelScope.launch {
            loadFeed()
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
