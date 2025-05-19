package com.mungkive.application.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


@Composable
fun FeedListView(
    viewModel: FeedViewModel = viewModel(),
    onWriteClick: () -> Unit,
    onFeedClick: (FeedData) -> Unit
) {
    val feedList by viewModel.feedList.collectAsState()

    // 최초 진입 시 서버에서 피드 데이터 가져오기
    LaunchedEffect(Unit) {
        viewModel.fetchFeeds()
    }

    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // 1. 타이틀을 첫 번째 item으로
        item {
            Text(
                text = "피드",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.W700,
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 19.dp, top = 32.dp, bottom = 4.dp)
            )
        }

        // 2. 피드 카드들
        items(feedList) { feed ->
            FeedCard(feed = feed, onClick = { onFeedClick(feed) })
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun FeedListViewPreview() {
    FeedListView(onFeedClick = {}, onWriteClick = {})
}
