package com.mungkive.application.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mungkive.application.core.RequestLimiter
import com.mungkive.application.core.TokenManager
import com.mungkive.application.network.ApiService
import com.mungkive.application.network.NetworkModule.BASE_URL
import com.mungkive.application.network.dto.LoginRequest
import com.mungkive.application.network.dto.ProfileEditRequest
import com.mungkive.application.network.dto.RegisterRequest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

object RequestLimits {
    val LOGIN = RequestLimiter("AuthLimit", 5,  TimeUnit.DAYS.toMillis(1))
    val LIKES = RequestLimiter("LikeLimit",10, TimeUnit.HOURS.toMillis(1))
}

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
    var profilePictureBase64 by mutableStateOf("")
        private set
    var profilePictureUrl by mutableStateOf("")
        private set

    fun onNameChange(newName: String) = run { name = newName }
    fun onBreedChange(newBreed: String) = run { breed = newBreed }
    fun onAgeChange(newAge: String) = run { age = newAge }

    fun updateProfilePicture(base64: String) {
        profilePictureBase64 = base64
    }

    fun clearProfilePicture() {
        profilePictureBase64 = ""
        profilePictureUrl = ""
    }

    fun login(
        onLoginSuccess: (success: Boolean) -> Unit
    ) = viewModelScope.launch {
        if (!RequestLimits.LOGIN.tryAcquire()) {
            apiResult = "Login blocked: daily limit exceeded"
            onLoginSuccess(false)
            return@launch
        }

        try {
            val rsp = api.login(LoginRequest(id, pw))
            token = rsp.token
            tokenManager.saveToken(rsp.token)
            tokenManager.saveCredentials(id, pw)
            Log.i("Auth", "token: $token")
            apiResult = "Login Success"
            onLoginSuccess(true)
        } catch (e: Exception) {
            apiResult = "Login Failed: ${e.localizedMessage}"
            onLoginSuccess(false)
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
            val picPayload = if (profilePictureBase64.isNotBlank()) {
                profilePictureBase64
            } else {
                ""
            }

            Log.i("editProfile", "picPayload: $picPayload")

            val rsp = api.editProfile(ProfileEditRequest(
                name = name,
                breed = breed,
                age = age.toIntOrNull() ?: 0,
                profilePicture = picPayload
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

            profilePictureUrl = if (rsp.profilePicture.contains("uploads/")) {
                "${BASE_URL}/${rsp.profilePicture}"
            } else {
                ""
            }
            profilePictureBase64 = ""
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
