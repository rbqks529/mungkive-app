package com.mungkive.application.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MapView(modifier: Modifier = Modifier) {
    Text(
        text = "Map View",
        modifier = modifier
            .fillMaxSize()
    )
}