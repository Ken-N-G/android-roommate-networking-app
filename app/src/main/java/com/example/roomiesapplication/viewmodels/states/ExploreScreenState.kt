package com.example.roomiesapplication.viewmodels.states

import com.example.roomiesapplication.model.UserEntryData

data class ExploreScreenState(
    var SearchInput: String = "",
    var SearchResult: UserEntryData = UserEntryData(),
    var SearchErrorMsg: String = "",
    var SearchValid: Boolean = false,
    var ExploreErrorMsg: String = ""
)
