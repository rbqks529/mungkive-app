package com.mungkive.application.ui.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.mungkive.application.ui.feed.data.FeedData


@Composable
fun MapScreen(feedList: List<FeedData>, onFeedClicked: (String) -> Unit, onToggleLike:(String)->Unit){
    val (selectedFeedId, setSelectedFeedId) = remember { mutableStateOf<String?>(null) }

    val selectedFeed = selectedFeedId?.let { feedId ->
        feedList.find { it.id == feedId }
    }

    Box(Modifier.fillMaxSize()) {
        MapView(
            feedList = feedList,
            onFeedSelected = { feedId ->
                setSelectedFeedId(feedId) // ID만 저장
            },
            onMapClick = { setSelectedFeedId(null) }
        )

        // 애니메이션이 있는 하단 피드 카드
        AnimatedVisibility(
            visible = selectedFeedId != null,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            selectedFeed?.let { feed ->
                MapFeed(
                    feed = feed,
                    modifier = Modifier.padding(bottom = 1.dp),
                    onDetailClick = {
                        onFeedClicked(feed.id)
                    },
                    onToggleLike = { onToggleLike(feed.id) }
                )
            }
        }
    }
}