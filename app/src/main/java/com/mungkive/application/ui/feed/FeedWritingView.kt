package com.mungkive.application.ui.feed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import com.mungkive.application.R

@Composable
fun FeedWritingView(navController: NavHostController, modifier: Modifier = Modifier) {

    IconButton(onClick = {
        navController.popBackStack()
    }) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_back),
            contentDescription = null
        )
    }
    Text(
        text = "Feed Writing View",
        modifier = modifier
            .fillMaxSize()
    )
}