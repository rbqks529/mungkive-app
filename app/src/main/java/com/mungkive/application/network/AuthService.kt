package com.mungkive.application.network

import com.mungkive.application.network.dto.LoginRequest
import com.mungkive.application.network.dto.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("login")
    suspend fun login(@Body req: LoginRequest): TokenResponse
}
