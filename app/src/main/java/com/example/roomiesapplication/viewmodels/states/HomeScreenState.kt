package com.example.roomiesapplication.viewmodels.states

import androidx.compose.runtime.mutableStateListOf
import com.example.roomiesapplication.model.FeedItem

data class HomeScreenState(
    var IsLoading: Boolean = false,
    var FeedList: MutableList<FeedItem> = mutableStateListOf(),
    var HomeErrorMsg: String = "refreshing",
    var HasReachedLowerLimit: Boolean = false,
    var currentUID: String = ""
)

