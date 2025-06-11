package com.mungkive.application.ui.feed.data

data class FeedData(
    val id: String,
    val userPic: String, //유저 프로필 이미지
    val userName: String,   // 유저 이름
    val userBreed: String,  // 유저 품종
    val content: String,
    val locate: String = "", // 게시물 위치 (좌표)
    val locName: String,   // 게시물 위치
    val picture: String,
    val likes: Int,
    val commentCount: Int,
    val date: String,
    val isLiked: Boolean,
)