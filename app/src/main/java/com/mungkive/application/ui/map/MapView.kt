package com.mungkive.application.ui.map

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mungkive.application.ui.feed.data.FeedData
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import kotlin.collections.getOrNull

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapView(
    modifier: Modifier = Modifier,
    feedList: List<FeedData>,
    onFeedSelected: (String) -> Unit
) {
    val context = LocalContext.current
    // FeedData에 GPS 좌표 추가 (LatLng)
    // 추후에 FeedData에 좌표값 추가되면 수정필요
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
        // 카메라 설정 및 현재 위치 갱신 버튼
//        properties = MapProperties(
//            LocationTrackingMode.
//        )
    ) {
        markerFeedList.forEach { marker ->
            val urlImg = marker.feed?.picture
            if (urlImg != null) {
                MarkerWithUrlIcon(
                    position = marker.position,
                    url = urlImg,
                    context = context,
                    onClick = {
                        marker.feed?.let { onFeedSelected(it.id) }
                        true
                    }
                )
            }
        }
    }
}
