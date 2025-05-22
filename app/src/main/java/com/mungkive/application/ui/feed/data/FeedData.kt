package com.mungkive.application.ui.feed.data

data class FeedData(
    val id: String,
    val userProfileUrl: String,
    val userName: String,
    val userBreed: String,
    val location: String,
    val imageUrl: String,
    val likeCount: Int,
    val commentCount: Int,
    val date: String,
    val content: String
)