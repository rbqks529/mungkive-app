package com.mungkive.application.ui.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfileView(modifier: Modifier = Modifier) {
    Text(
        text = "Profile View",
        modifier = modifier
            .fillMaxSize()
    )
}