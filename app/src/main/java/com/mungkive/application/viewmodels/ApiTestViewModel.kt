package com.mungkive.application.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mungkive.application.core.TokenManager
import com.mungkive.application.network.ApiService
import com.mungkive.application.network.dto.LoginRequest
import com.mungkive.application.network.dto.ProfileEditRequest
import com.mungkive.application.network.dto.RegisterRequest
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

    fun clearIdAndPw() {
        id = ""
        pw = ""
    }

    // Profile
    var name by mutableStateOf("")
        private set
    var breed by mutableStateOf("")
        private set
    var age by mutableStateOf("")
        private set
    var profilePicture by mutableStateOf("")

    fun onNameChange(newName: String) = run { name = newName }
    fun onBreedChange(newBreed: String) = run { breed = newBreed }
    fun onAgeChange(newAge: String) = run { age = newAge }

    fun clearProfilePicture() {
        profilePicture = ""
    }

    fun login(
        onLoginSuccess: () -> Unit
    ) = viewModelScope.launch {
        try {
            val rsp = api.login(LoginRequest(id, pw))
            token = rsp.token
            tokenManager.saveToken(rsp.token)
            tokenManager.saveCredentials(id, pw)
            Log.i("Auth", "token: $token")
            apiResult = "Login Success"
            onLoginSuccess()
        } catch (e: Exception) {
            apiResult = "Login Failed: ${e.localizedMessage}"
        }
    }

    fun register(
        onRegisterSuccess: (success: Boolean) -> Unit,
    ) = viewModelScope.launch {
        try {
            val rsp = api.register(RegisterRequest(id, pw))
            token = rsp.token
            tokenManager.saveToken(rsp.token)
            tokenManager.saveCredentials(id, pw)
            Log.i("Auth", "token: $token")
            apiResult = "Register Success"
            onRegisterSuccess(true)
        } catch (e: Exception) {
            apiResult = "Register Failed: ${e.localizedMessage}"
            onRegisterSuccess(false)
        }
    }

    fun editProfile(
        onSuccess: (success: Boolean) -> Unit,
    ) = viewModelScope.launch {
        try {
            val rsp = api.editProfile(ProfileEditRequest(
                name = name,
                breed = breed,
                age = if (age.toIntOrNull() != null) {
                    age.toInt()
                } else {
                    0
                },
                profilePicture = profilePicture
            ))
            val result = rsp.message
            onSuccess(true)
        } catch (e: Exception) {
            apiResult = "Profile Edit Failed: ${e.localizedMessage}"
            onSuccess(false)
        }
    }

    fun getProfile() = viewModelScope.launch {
        try {
            val rsp = api.getProfile()
            apiResult = "Profile Get Success"
            name = rsp.name
            breed = rsp.breed
            age = rsp.age.toString()
            profilePicture = rsp.profilePicture
        } catch (e: Exception) {
            apiResult = "Profile Failed: ${e.localizedMessage}"
        }
    }

    fun tryAutoLogin(
        onAutoLoginFinished: (success: Boolean) -> Unit
    ) = viewModelScope.launch {
        val creds = tokenManager.getCredentials()
        if (creds == null) {
            onAutoLoginFinished(false)
            return@launch
        }

        val (savedId, savedPw) = creds
        try {
            val rsp = api.login(LoginRequest(savedId, savedPw))
            token = rsp.token
            tokenManager.saveToken(rsp.token)
            id = savedId
            pw = savedPw
            apiResult = "Auto-login success, token: $token"
            onAutoLoginFinished(true)
        } catch (e: Exception) {
            apiResult = "Auto-login failed: ${e.localizedMessage}"
            onAutoLoginFinished(false)
        }
    }

    /*fun listPosts() = viewModelScope.launch {
        try {
            val rsp = api.listPosts()
            apiResult = rsp.joinToString("\n")
        } catch (e: Exception) {
            apiResult = "Post Failed: ${e.localizedMessage}"
        }
    }*/
}
