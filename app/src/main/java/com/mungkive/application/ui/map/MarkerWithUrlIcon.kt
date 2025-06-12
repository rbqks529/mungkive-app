package com.mungkive.application.ui.map

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mungkive.application.ui.map.ImageConverter.loadUrlToOverlayImage
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.rememberMarkerState
import com.naver.maps.map.overlay.OverlayImage

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MarkerWithUrlIcon(
    position: LatLng,
    url: String,
    context: Context,
    onClick: () -> Boolean,
    modifier: Modifier = Modifier
) {
    val overlayImageState = remember { mutableStateOf<OverlayImage?>(null) }

    LaunchedEffect(url) {
        val result = loadUrlToOverlayImage(context, url)
        Log.d("OverlayDebug", "OverlayImage 결과: $result")
        overlayImageState.value = result
    }

    if (overlayImageState.value != null) {
        Log.d("OverlayDebug", "Marker 생성됨 at $position")
    }

    overlayImageState.value?.let { overlay ->
        Marker(
            state = rememberMarkerState(position = position),
            icon = overlay,
            width = 45.dp,
            height = 45.dp,
            onClick = {
                onClick()
                true
            }
        )
    }
}