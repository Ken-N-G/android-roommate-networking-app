package com.example.roomiesapplication.viewmodels.states

data class ProfileScreenState(
    var Username: String = "",
    var Email: String = "",
    var Fullname: String = "",
    var ProfileDescription: String = "",
    var Uid: String = "",
    var ProfileURL: String = "",
    var AcceptingStatus: Boolean = true,
    var ProfileErrorMsg: String = ""
)
