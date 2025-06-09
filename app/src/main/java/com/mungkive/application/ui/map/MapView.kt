package com.mungkive.application.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mungkive.application.ui.feed.data.FeedData
import com.mungkive.application.ui.map.Functions.toLatlng
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapView(
    modifier: Modifier = Modifier,
    feedList: List<FeedData>,
    onFeedSelected: (String) -> Unit,
    onMapClick: () -> Unit
) {
    val context = LocalContext.current
    // 네이버 맵 뷰 초기 위치 설정
    val cameraPositionState = rememberCameraPositionState {
        // 카메라 위치를 가장 첫 게시물의 위치 기준으로 잡음.
        position = CameraPosition(feedList[0]
            .locate
            .toLatlng()?:LatLng(37.5413, 127.0793),
            15.0
        )
    }

    NaverMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapClick = { _,_ ->
            onMapClick()
        },
        uiSettings = MapUiSettings(isLocationButtonEnabled = true)
        // 현재 위치 맵에 띄우기
//        properties = MapProperties(
//            LocationTrackingMode.follow
//        )
    ) {
        feedList.forEach { marker ->
            val urlImg = marker.userPic
            val location = marker.locate.toLatlng()
            if (urlImg != null && location != null) {
                MarkerWithUrlIcon(
                    position = location,
                    url = urlImg,
                    context = context,
                    onClick = {
                        marker.let { onFeedSelected(it.id) }
                        true
                    }
                )
            }
        }
    }
}
