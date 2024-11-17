package com.example.roomiesapplication.model

import java.util.Date

data class MessageItemData(
    val sender: String,
    val content: String,
    val dateSent: Date
)
