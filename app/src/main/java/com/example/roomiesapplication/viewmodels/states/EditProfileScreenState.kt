package com.example.roomiesapplication.viewmodels.states

data class EditProfileScreenState(
    var Username: String = "",
    var Fullname: String = "",
    var ProfileDescription: String = "",
    var IsAcceptingRoommates: Boolean = true,
    var UsernameErrorMsg: String = "",
    var FullnameErrorMsg: String = "",
    var ProfileDescriptionError: String = "",
    var EditProfileErrorMsg: String = "",
    var Uid: String = "",
    var UsernameValid: Boolean = false,
    var FullnameValid: Boolean = false,
    var ProfileDescriptionValid: Boolean = false,
    var PreviousUsername: String = ""
)
