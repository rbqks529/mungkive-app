package com.mungkive.application.ui.map

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mungkive.application.ui.feed.FeedData
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapView(
    modifier: Modifier = Modifier,
    feedList: List<FeedData>,
    onFeedSelected: (String) -> Unit
) {
    LaunchedEffect(feedList) {
        Log.d("FeedDebug", "feedList 크기: ${feedList.size}")
        feedList.forEachIndexed { i, feed ->
            Log.d("FeedDebug", "feed[$i].imageUrl = '${feed.imageUrl}'")
        }
    }

    val context = LocalContext.current

    val markerFeedList = listOf(
        MapMarkerData(LatLng(37.5413, 127.0793), feed = feedList.getOrNull(0)),
        MapMarkerData(LatLng(37.5435, 127.0774), feed = feedList.getOrNull(1)),
        MapMarkerData(LatLng(37.5404, 127.0793), feed = null)
    )

    // 네이버 맵 뷰 초기 위치 설정
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(markerFeedList[0].position, 15.0)
    }

    NaverMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        markerFeedList.forEach { marker ->
            val urlImg = marker.feed?.imageUrl
            if (urlImg != null) {
                MarkerWithUrlIcon(
                    position = marker.position,
                    url = urlImg,
                    context = context,
                    onClick = {
                        Log.d("MapView", "🖱️ 마커 클릭됨: ${marker.feed?.id}")
                        marker.feed?.let { onFeedSelected(it.id) }
                        true
                    }
                )
            } else {
                Log.w("OverlayDebug", "urlImg 없음 → 마커 생략")
            }
        }
    }
}
