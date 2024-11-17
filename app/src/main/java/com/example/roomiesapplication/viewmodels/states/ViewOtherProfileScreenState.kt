package com.example.roomiesapplication.viewmodels.states

import com.example.roomiesapplication.model.UserEntryData

data class ViewOtherProfileScreenState(
    var UserEntryData: UserEntryData = UserEntryData(),
    var ProfileErrorMsg: String = ""
)
