package com.mungkive.application.ui.map

import android.content.Context
import coil3.Bitmap
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowHardware
import coil3.size.Size
import coil3.toBitmap
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Url -> Bitmap -> OverlayImag(Naver.map 요구 형식) 변환 함수
suspend fun loadUrlToOverlayImage(context: Context, imageUrl: String): OverlayImage? {
    return withContext(Dispatchers.IO) {
        val loader = ImageLoader.Builder(context)
            .allowHardware(false) // Bitmap 생성 허용
            .build()

        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build()

        val result = loader.execute(request)

        if (result is SuccessResult) {
            val image = result.image
            val bitmap: Bitmap? = image.toBitmap()
            bitmap?.let { OverlayImage.fromBitmap(it) }
        } else {
            null
        }
    }
}