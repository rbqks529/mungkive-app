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
        AnimatedVisibility(
            visible = selectedFeed != null,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight }, // 아래에서 시작
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300)),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> fullHeight }, // 아래로 사라짐
                animationSpec = tween(
                    durationMillis = 250,
                    easing = FastOutLinearInEasing
                )
            ) + fadeOut(animationSpec = tween(250)),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            selectedFeed?.let { feed ->
                MapFeed(
                    feed = feed,
                    modifier = Modifier.padding(bottom = 4.dp),
                    onDetailClick = {
                        onFeedClicked(feed.id)
                    },
                    onToggleLike = { onToggleLike(feed.id) }
                )
            }
        }
    }
}