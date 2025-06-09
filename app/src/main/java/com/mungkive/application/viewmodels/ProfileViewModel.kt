package com.mungkive.application.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mungkive.application.models.ProfileViewStatus
import com.mungkive.application.models.profile.ProfileData
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.io.encoding.ExperimentalEncodingApi

class ProfileViewModel() : ViewModel() {
    @OptIn(ExperimentalEncodingApi::class)
    private val _profile = mutableStateOf<ProfileData>(
        ProfileData(
            name = null,
            breed = null,
            age = null,
            imageBase64 = null
        )
    )
    val profile = _profile
    var status = mutableStateOf(ProfileViewStatus.VIEW)

    fun setStatus(newStatus: ProfileViewStatus) {
        status.value = newStatus
    }

    // fetch profile data from server
    fun fetchProfileData() {
        //
    }

    // upload profile data to server
    fun uploadProfileData() {
        //
    }
}
