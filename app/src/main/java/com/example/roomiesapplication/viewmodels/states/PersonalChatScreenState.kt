package com.example.roomiesapplication.viewmodels.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.roomiesapplication.model.MessageItemData

data class PersonalChatScreenState(
    var MessageList: MutableList<MessageItemData> = mutableStateListOf(),
    var MessageContent: String = "",
    var SelfUsername: String = "",
    var OtherUsername: String = "",
    var PersonalChatErrorMsg: String = "",
    var MessageValid: Boolean = false,
    var SelfUID: String = "",
    var OtherUID: String = ""
)
