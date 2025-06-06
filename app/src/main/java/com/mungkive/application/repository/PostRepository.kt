package com.mungkive.application.repository

import com.mungkive.application.network.ApiService
import com.mungkive.application.network.dto.CommentResponse
import com.mungkive.application.network.dto.PostCreateRequest
import retrofit2.Response

class PostRepository(
    private val apiService: ApiService,
    private val token: String   // 하드코딩 토큰 사용
) {

    // TODO: 헤더에 토큰 넣는거 뺴기
    suspend fun createPost(request: PostCreateRequest): Response<Void> {
        return apiService.createPost("Bearer $token", request)
    }

    suspend fun getComments(postId: Long): List<CommentResponse> {
        return apiService.getComments("Bearer $token", postId)
    }
}
