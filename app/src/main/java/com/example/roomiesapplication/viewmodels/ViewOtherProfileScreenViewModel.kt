package com.example.roomiesapplication.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomiesapplication.model.UserEntryData
import com.example.roomiesapplication.viewmodels.states.ViewOtherProfileScreenState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ViewOtherProfileScreenViewModel(uid: String): ViewModel() {

    private val _viewOtherProfileScreenState = mutableStateOf((ViewOtherProfileScreenState()))
    val viewOtherProfileScreenState: State<ViewOtherProfileScreenState> = _viewOtherProfileScreenState

    val uid = uid

    init {
        viewModelScope.launch {
            fetchUserData(uid)
        }
    }

    private suspend fun fetchUserData(uid: String) {
        val uid = uid
        val db = FirebaseFirestore.getInstance()
        try {
            val docSnapshot = db.collection("users").document(uid).get().await()
            val userData = UserEntryData(
                username = docSnapshot.getString("username").orEmpty(),
                fullname = docSnapshot.getString("fullname").orEmpty(),
                profileDescription = docSnapshot.getString("profileDescription").orEmpty(),
                profileURL = docSnapshot.getString("profileURL").orEmpty(),
                uid = uid,
                acceptingStatus = docSnapshot.getBoolean("acceptingStatus") ?: false
            )
            _viewOtherProfileScreenState.value = _viewOtherProfileScreenState.value.copy(
                UserEntryData = userData
            )
        } catch (error: Exception) {
            _viewOtherProfileScreenState.value.ProfileErrorMsg = error.toString()
        }
    }

    fun OnRefresh() {
        viewModelScope.launch {
            fetchUserData(uid = uid)
        }
    }
}