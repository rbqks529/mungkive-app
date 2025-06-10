package com.mungkive.application.network.dto

data class RegisterRequest(val id: String, val password: String)
data class LoginRequest(val id: String, val password: String)
data class TokenResponse(val token: String)

// dto/ProfileDto.kt
data class ProfileEditRequest(
    val name: String,
    val breed: String,
    val age: Int,
    val profilePicture: String      // Base64
)

data class ProfileResponse(
    val userId: String,
    val name: String,
    val breed: String,
    val age: Int,
    val profilePicture: String
)

// dto/PostDto.kt
data class PostCreateRequest(
    val content: String,
    val picture: String,
    val locate: String,
    val locName: String,
    val likes: Int = 0
)

data class PostResponse(
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

// dto/CommentDto.kt
data class CommentCreateRequest(val content: String)
data class CommentResponse(
    val id: Long,
    val postId: Long,       // 추가
    val userName: String,     // 추가 (닉네임 또는 id)
    val userPic: String,    // 추가 (프로필 사진 경로)
    val content: String,
    val created: String     // 추가 (작성 시간)
)


// dto/Common.kt
data class MsgResponse(val message: String? = null)
