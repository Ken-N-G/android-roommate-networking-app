package com.example.roomiesapplication.model

import java.util.Date

data class ChatItemData(
    val username: String,
    val lastMessageSent: String,
    val dateLastMessageSent: Date,
    val profileURL: String,
    val uid: String
)
