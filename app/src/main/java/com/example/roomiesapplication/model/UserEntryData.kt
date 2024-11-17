package com.example.roomiesapplication.model

data class UserEntryData(
    var username: String = "",
    var email: String = "",
    var fullname: String = "",
    var profileDescription: String = "",
    var uid: String = "",
    var profileURL: String = "",
    var acceptingStatus: Boolean = false
)
