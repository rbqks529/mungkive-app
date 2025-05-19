package com.mungkive.application.ui.feed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FeedListView(modifier: Modifier = Modifier) {
    Text(
        text = "Feed List View",
        modifier = modifier
            .fillMaxSize()
    )
}