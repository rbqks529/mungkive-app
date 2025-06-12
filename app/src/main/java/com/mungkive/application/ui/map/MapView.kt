package com.mungkive.application.ui.map

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
    val defaultLocation = LatLng(37.5665, 126.9780)
    val fallbackFeedList = remember {
        listOf(
            FeedData(
                id = "1",
                userPic = "https://images.unsplash.com/photo-1518717758536-85ae29035b6d",
                userName = "피드를 추가해보세요!",
                userBreed = "시바견",
                content = "오늘 공원 산책 너무 즐거웠어요!",
                locate = "126.9780,37.5665",
                locName = "건국대학교",
                picture = "https://images.unsplash.com/photo-1558788353-f76d92427f16",
                likes = 5,
                commentCount = 2,
                date = "2025-06-01 10:15:00",
                isLiked = true
            )
        )
    }
    val validList = if (feedList.isNotEmpty()) feedList else fallbackFeedList


    // 네이버 맵 뷰 초기 위치 설정
    val cameraPositionState = rememberCameraPositionState {
        // 카메라 위치를 가장 첫 게시물의 위치 기준으로 잡음.
        position = CameraPosition(
            validList[0]
                .locate
                .toLatlng()?:defaultLocation,
            15.0
        )
        Log.d("MapView", "MapView: $position")
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
        validList.forEach { marker ->
            val urlImg = marker.userPic
            val location = marker.locate.toLatlng()
            if (location != null) {
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
