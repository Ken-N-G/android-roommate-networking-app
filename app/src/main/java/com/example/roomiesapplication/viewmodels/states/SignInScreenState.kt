package com.example.roomiesapplication.viewmodels.states

data class SignInScreenState(
    var Email: String = "",
    var Password: String = "",
    var EmailErrorMsg: String = "",
    var PasswordErrorMsg: String = "",
    var SignInErrorMsg: String = "",
    var IsLoading: Boolean = false
)
