package com.mungkive.application.network

import com.mungkive.application.core.TokenManager
import com.mungkive.application.network.dto.LoginRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenManager: TokenManager,
    private val loginService: AuthService
) : Interceptor {
    private val lock = Any()

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        runBlocking { tokenManager.getToken() }?.let { token ->
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        }

        var response = chain.proceed(request)
        if (response.code != 401) return response

        response.close()

        synchronized(lock) {
            val freshToken = runBlocking { tokenManager.getToken() }
            if (freshToken == null) {
                runBlocking {
                    tokenManager.getCredentials()?.let { (id, password) ->
                        try {
                            val newToken = loginService.login(LoginRequest(id, password)).token
                            tokenManager.saveToken(newToken)
                        } catch (e: Exception) {
                            tokenManager.clearAll()
                            throw e
                        }
                    } ?: tokenManager.clearAll()
                }
            }
        }

        val retryToken = runBlocking { tokenManager.getToken() }
        val newRequest = request.newBuilder()
            .removeHeader("Authorization")
            .addHeader("Authorization", "Bearer $retryToken")
            .build()

        return chain.proceed(newRequest)
    }
}
