package com.mungkive.application.ui.tip

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TipListView(modifier: Modifier = Modifier) {
    Text(
        text = "Tip List View",
        modifier = modifier
            .fillMaxSize()
    )
}