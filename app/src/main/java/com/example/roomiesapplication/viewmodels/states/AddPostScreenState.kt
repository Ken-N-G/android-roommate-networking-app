package com.example.roomiesapplication.viewmodels.states

data class AddPostScreenState(
    var AddPostErrorMsg: String = "",
    var PostContentErrorMsg: String = "",
    var PostValid: Boolean = false,
    var PostContent: String = ""
)
