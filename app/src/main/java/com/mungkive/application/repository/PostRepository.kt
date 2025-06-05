package com.mungkive.application.repository

import com.mungkive.application.network.ApiService
import com.mungkive.application.network.dto.PostCreateRequest
import retrofit2.Response

class PostRepository(
    private val apiService: ApiService,
    private val token: String   // 하드코딩 토큰 사용
) {
    suspend fun createPost(request: PostCreateRequest): Response<Void> {

        // TODO: 헤더에 토큰 넣는거 뺴기 
        return apiService.createPost("Bearer $token", request)
    }
}
