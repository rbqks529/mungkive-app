package com.mungkive.application.ui.map

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
                userName = "댕댕1",
                userBreed = "시바견",
                content = "오늘 공원 산책 너무 즐거웠어요!",
                locate = "126.9780,37.5665",
                locName = "서울시청",
                picture = "https://images.unsplash.com/photo-1558788353-f76d92427f16",
                likes = 5,
                commentCount = 2,
                date = "2025-06-01 10:15:00",
                isLiked = true
            ),
            FeedData(
                id = "2",
                userPic = "https://images.unsplash.com/photo-1507146426996-ef05306b995a",
                userName = "냥냥이",
                userBreed = "코리안숏헤어",
                content = "햇살 좋은 날 창가에서 쉬는 중~",
                locate = "127.0276,37.4979",
                locName = "강남역",
                picture = "https://images.unsplash.com/photo-1518715308788-30057527ade5",
                likes = 10,
                commentCount = 1,
                date = "2025-06-02 12:34:56",
                isLiked = false
            ),
            FeedData(
                id = "3",
                userPic = "https://images.unsplash.com/photo-1465101046530-73398c7f28ca",
                userName = "뽀삐",
                userBreed = "푸들",
                content = "새로운 친구들과 놀았어요!",
                locate = "126.9425,37.5610",
                locName = "홍대입구",
                picture = "https://images.pexels.com/photos/1108099/pexels-photo-1108099.jpeg",
                likes = 8,
                commentCount = 3,
                date = "2025-06-03 15:20:10",
                isLiked = true
            ),
            FeedData(
                id = "4",
                userPic = "https://images.pexels.com/photos/1805164/pexels-photo-1805164.jpeg",
                userName = "순돌이",
                userBreed = "골든리트리버",
                content = "강아지 카페에서 간식 파티!",
                locate = "127.0427,37.5013",
                locName = "건대입구",
                picture = "https://images.pexels.com/photos/4587997/pexels-photo-4587997.jpeg",
                likes = 12,
                commentCount = 4,
                date = "2025-06-04 18:05:30",
                isLiked = true
            ),
            FeedData(
                id = "5",
                userPic = "https://cdn.pixabay.com/photo/2016/02/19/10/00/dog-1207816_1280.jpg",
                userName = "초코",
                userBreed = "말티즈",
                content = "비 온 뒤 산책길이 너무 상쾌했어요.",
                locate = "127.0618,37.5157",
                locName = "잠실",
                picture = "https://cdn.pixabay.com/photo/2017/09/25/13/12/dog-2785074_1280.jpg",
                likes = 7,
                commentCount = 0,
                date = "2025-06-05 09:55:00",
                isLiked = false
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
        //uiSettings = MapUiSettings(isLocationButtonEnabled = true)
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
