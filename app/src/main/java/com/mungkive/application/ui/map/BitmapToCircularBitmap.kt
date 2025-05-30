package com.mungkive.application.ui.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.core.graphics.createBitmap
import androidx.core.graphics.scale

//임시로 만든 함수, 추후에 가능하면 coil 기반으로 수정 예정 - 최적화 이슈
suspend fun BitmapToCircularBitmap(
    bitmap: Bitmap,
    targetSize: Int
): Bitmap? = withContext(Dispatchers.IO) {
    try {
        // 리사이징 (작은 쪽 기준으로 targetSize 이상 유지 + 보간 필터 적용)
        val scale = targetSize / bitmap.width.coerceAtMost(bitmap.height).toFloat()
        val scaledW = (bitmap.width * scale).toInt()
        val scaledH = (bitmap.height * scale).toInt()
        val scaledBitmap = bitmap.scale(scaledW, scaledH,true)

        // 중앙 crop
        val x = ((scaledBitmap.width - targetSize) / 2).coerceAtLeast(0)
        val y = ((scaledBitmap.height - targetSize) / 2).coerceAtLeast(0)
        val squareBitmap = Bitmap.createBitmap(scaledBitmap, x, y, targetSize, targetSize)

        // 원형 마스킹
        val output = createBitmap(targetSize, targetSize, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        // 테두리 처리
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        val path = Path().apply {
            addCircle(targetSize / 2f, targetSize / 2f, targetSize / 2f, Path.Direction.CCW)
        }
        canvas.clipPath(path)
        canvas.drawBitmap(squareBitmap, 0f, 0f, paint)

        output
    } catch (e: Exception) {
        Log.e("CircularMaskingDebug", "원형 마스킹 실패: ${e.message}")
        null
    }
}