package com.mungkive.application.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mungkive.application.R
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapView
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberMarkerState
import com.naver.maps.map.overlay.OverlayImage

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapView(modifier: Modifier = Modifier) {
    //테스트 용 건국대주변 좌표
    val markerList = listOf(
        LatLng(37.5413, 127.0793), // 공학관
        LatLng(37.5435, 127.0774), // 새천년관
        LatLng(37.5404, 127.0793)  // 신공학관
    )

    val markerIcon = OverlayImage.fromResource(R.drawable.dummyprofile)

    val cameraPositionState = rememberCameraPositionState{position = CameraPosition(markerList[0], 15.0)}
    NaverMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ){
        markerList.forEach { latLng ->
            Marker(
                state = rememberMarkerState(position = latLng),
                icon = markerIcon,
                width = 40.dp,
                height = 40.dp
            )
        }
    }
}
