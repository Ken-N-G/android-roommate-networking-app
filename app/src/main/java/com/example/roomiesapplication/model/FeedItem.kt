package com.example.roomiesapplication.model

import java.util.Date

data class FeedItem(
    val profileURL: String,
    val username: String,
    val datePosted: Date,
    val postContent: String,
    val uid: String
)
