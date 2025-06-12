package com.mungkive.application.ui.map

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ModalBottomSheetDefaults.properties
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mungkive.application.ui.feed.data.FeedData
import com.mungkive.application.ui.map.Functions.toLatlng
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import kotlinx.coroutines.launch


@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapView(
    modifier: Modifier = Modifier,
    feedList: List<FeedData>,
    onFeedSelected: (String) -> Unit,
    onMapClick: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val defaultLocation = LatLng(37.5665, 126.9780)

    val fallbackFeedList = remember {
        listOf(
            FeedData(
                id = "1",
                userPic = "9TeWbn6N1nmcwKgOc4xxPeAULYIHwc7WJcWzuxPNaFFeDUD55nKQQn46eF0gz_aJIKyIQPntUewca7HaoOmrXN4oGqvc9edp_7Rd5yNfCdZC1axsOdO5mIzGGzsTEDyzBe4JNMvs7axbdvNhEkMaQ",
                userName = "멍카이브를 시작해보세요!",
                userBreed = "도지",
                content = "하단의 피드탭을 클릭하여 피드를 추가",
                locate = "126.9780,37.5665",
                locName = "건국대학교",
                picture = "9TeWbn6N1nmcwKgOc4xxPeAULYIHwc7WJcWzuxPNaFFeDUD55nKQQn46eF0gz_aJIKyIQPntUewca7HaoOmrXN4oGqvc9edp_7Rd5yNfCdZC1axsOdO5mIzGGzsTEDyzBe4JNMvs7axbdvNhEkMaQ",
                likes = 123,
                commentCount = 456,
                date = "2025-06-01 10:15:00",
                isLiked = true
            )
        )
    }
    val validList = if (feedList.isNotEmpty()) feedList else fallbackFeedList

    // 맵 세팅값
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
    var mapProperties by remember {
        mutableStateOf(
            MapProperties(
                maxZoom = 20.0,
                minZoom = 5.0,
                locationTrackingMode = LocationTrackingMode.None
            )
        )
    }
    var mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                isLocationButtonEnabled = true
            )
        )
    }
    NaverMap(
        modifier = Modifier.fillMaxSize(),
        locationSource = rememberFusedLocationSource(),
        cameraPositionState = cameraPositionState,
        onMapClick = { _,_ ->
            onMapClick()
        },
        onMapDoubleTab = { _,_ ->
            val currentZoom = cameraPositionState.position.zoom
            val newZoom = (currentZoom + 1.0).coerceAtMost(20.0) // 최대 줌 제한
            coroutineScope.launch {
                cameraPositionState.animate(
                    CameraUpdate.zoomTo(newZoom),
                    durationMs = 300
                )
            }
            true
        },
        uiSettings =  mapUiSettings,
        properties = mapProperties
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
