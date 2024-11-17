package com.example.roomiesapplication.viewmodels.states

import androidx.compose.runtime.mutableStateListOf
import com.example.roomiesapplication.model.ChatItemData

data class ChatScreenState(
    val ChatList: MutableList<ChatItemData> = mutableStateListOf(),
    var ChatErrorMsg: String = ""
)
