package com.mungkive.application.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import com.mungkive.application.ui.feed.data.FeedData


@Composable
fun MapScreen(feedList: List<FeedData>, onFeedClicked: (String) -> Unit) {
    val (selectedFeed, setSelectedFeed) = remember { mutableStateOf<FeedData?>(null) }

    Box(Modifier.fillMaxSize()) {
        MapView(
            feedList = feedList,
            onFeedSelected = { feedId ->
                // feedId로 FeedData 찾기
                setSelectedFeed(feedList.find { it.id == feedId })
            },
            onMapClick = { setSelectedFeed(null) }
        )
        // 하단 피드 카드
        if (selectedFeed != null) {
            MapFeed(
                feed = selectedFeed,
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                onDetailClick = {
                    onFeedClicked(selectedFeed.id)
                }
            )
        }
    }
}