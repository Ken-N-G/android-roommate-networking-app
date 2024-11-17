package com.example.roomiesapplication.viewmodels.states

data class RegistrationScreenState(
    var Username: String = "",
    var Password: String = "",
    var Fullname: String = "",
    var Email: String = "",
    var UsernameErrorMsg: String = "",
    var PasswordErrorMsg: String = "",
    var FullnameErrorMsg: String = "",
    var EmailErrorMsg: String = "",
    var RegistrationErrorMsg: String = "",
    var IsLoading: Boolean = false,
    var EmailValid: Boolean = false,
    var UsernameValid: Boolean = false,
    var PasswordValid: Boolean = false,
    var FullnameValid: Boolean = false,
    var RegistrationSuccessful: Boolean = false
)
