package com.mungkive.application.util

import android.content.Context
import android.net.Uri
import android.util.Base64
import java.io.InputStream

fun uriToBase64(context: Context, imageUri: Uri): String? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
        val bytes = inputStream?.readBytes()
        inputStream?.close()
        bytes?.let { Base64.encodeToString(it, Base64.NO_WRAP) }
    } catch (e: Exception) {
        null
    }
}
