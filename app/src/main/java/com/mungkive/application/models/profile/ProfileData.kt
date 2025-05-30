package com.mungkive.application.models.profile

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

data class ProfileData @OptIn(ExperimentalEncodingApi::class) constructor(
    var name: String?,
    var breed: String?,
    var age: Int?,
    var imageBase64: Base64?
)
