package com.example.roomiesapplication.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.roomiesapplication.model.ErrorMsgConstants
import com.example.roomiesapplication.viewmodels.states.SignInScreenState
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class SignInScreenViewModel: ViewModel() {

    private val _signInState = mutableStateOf(SignInScreenState())
    val signInState: State<SignInScreenState> = _signInState

    suspend fun SignIn(): AuthResult? {
        return try {
            _signInState.value.IsLoading = true
            val response = Firebase.auth.signInWithEmailAndPassword(
                _signInState.value.Email,
                _signInState.value.Password
            ).await()
            _signInState.value.SignInErrorMsg = ErrorMsgConstants.ON_SIGNIN_SUCCESS
            _signInState.value.IsLoading = false
            response
        } catch(error: Exception) {
            _signInState.value.SignInErrorMsg = error.toString()
            _signInState.value.IsLoading = false
            null
        }
    }

    fun OnEmailFieldChange(change: String) {
        _signInState.value = signInState.value.copy(
            Email = change
        )
    }

    fun OnPasswordFieldChange(change: String) {
        _signInState.value = signInState.value.copy(
            Password = change
        )
    }
}