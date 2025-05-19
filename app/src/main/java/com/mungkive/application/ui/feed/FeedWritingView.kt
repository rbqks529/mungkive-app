package com.mungkive.application.ui.feed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun FeedWritingView(navController: NavHostController, modifier: Modifier = Modifier) {

    IconButton(onClick = {
        navController.popBackStack()
    }) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = null
        )
    }
    Text(
        text = "Feed Writing View",
        modifier = modifier
            .fillMaxSize()
    )
}