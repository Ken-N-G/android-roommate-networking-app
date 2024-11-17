package com.example.roomiesapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomiesapplication.model.ErrorMsgConstants
import com.example.roomiesapplication.model.UserEntryData
import com.example.roomiesapplication.viewmodels.states.ExploreScreenState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ExploreScreenViewModel: ViewModel() {

    private val _exploreState = MutableStateFlow(ExploreScreenState())
    val exploreState: StateFlow<ExploreScreenState> = _exploreState.asStateFlow()

    fun Search() {
        _exploreState.value.SearchResult = UserEntryData()
        val db = FirebaseFirestore.getInstance()
        viewModelScope.launch {
            val username = fetchUsername()
            _exploreState.value.SearchValid  = username != _exploreState.value.SearchInput
            if (_exploreState.value.SearchValid) {
                try {
                    val userSnapshots = db.collection("users").get().await()
                    userSnapshots.forEach() { snapshot ->
                        val username = snapshot.getString("username").orEmpty()
                        if (username == _exploreState.value.SearchInput) {
                            val searchResult = UserEntryData(
                                username = username,
                                email = snapshot.getString("email").orEmpty(),
                                fullname = snapshot.getString("fullname").orEmpty(),
                                profileDescription = snapshot.getString("profileDescription").orEmpty(),
                                uid = snapshot.getString("uid").orEmpty(),
                                profileURL = snapshot.getString("profileURL").orEmpty()
                            )
                            _exploreState.value = _exploreState.value.copy(
                                SearchResult = searchResult
                            )
                        }
                    }
                } catch (error: Exception) {
                    _exploreState.value.ExploreErrorMsg = error.toString()
                }
            }
        }
    }

    private suspend fun fetchUsername(): String {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        val userSnapshot = FirebaseFirestore.getInstance().collection("users").document(uid).get().await()
        return userSnapshot.getString("username").orEmpty()
    }

    fun OnSearchInputFieldChange(change: String) {
        _exploreState.value = exploreState.value.copy(
            SearchInput = change
        )
        validateSearch()
    }

    private fun validateSearch() {
        val isEmpty = checkIsEmpty(
            input = _exploreState.value.SearchInput,
            onError = {
                _exploreState.value.SearchErrorMsg = ErrorMsgConstants.POST_IS_EMPTY
            }
        )
        val isValid = !isEmpty
        _exploreState.value.SearchValid = isValid
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