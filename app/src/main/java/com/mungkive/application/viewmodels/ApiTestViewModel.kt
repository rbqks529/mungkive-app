package com.mungkive.application.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mungkive.application.core.TokenManager
import com.mungkive.application.network.ApiService
import com.mungkive.application.network.dto.LoginRequest
import kotlinx.coroutines.launch

class ApiTestViewModel(
    private val api: ApiService,
    private val tokenManager: TokenManager
) : ViewModel() {
    var id by mutableStateOf("")
        private set
    var pw by mutableStateOf("")
        private set
    var token by mutableStateOf<String?>(null)
        private set
    var apiResult by mutableStateOf("no result")
        private set

    fun onIdChange(newId: String) = run { id = newId }
    fun onPwChange(newPw: String) = run { pw = newPw }

    fun login() = viewModelScope.launch {
        try {
            val rsp = api.login(LoginRequest(id, pw))
            token = rsp.token
            tokenManager.saveToken(rsp.token)
            tokenManager.saveCredentials(id, pw)
            apiResult = "Login Success"
        } catch (e: Exception) {
            apiResult = "Login Failed: ${e.localizedMessage}"
        }
    }

    fun getProfile() = viewModelScope.launch {
        try {
            val rsp = api.getProfile()
            apiResult = rsp.toString()
        } catch (e: Exception) {
            apiResult = "Profile Failed: ${e.localizedMessage}"
        }
    }

    fun listPosts() = viewModelScope.launch {
        try {
            val rsp = api.listPosts()
            apiResult = rsp.joinToString("\n")
        } catch (e: Exception) {
            apiResult = "Post Failed: ${e.localizedMessage}"
        }
    }
}
