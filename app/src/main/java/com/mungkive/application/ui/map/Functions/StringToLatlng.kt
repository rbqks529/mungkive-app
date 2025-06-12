package com.mungkive.application.ui.map.Functions

import android.util.Log
import com.naver.maps.geometry.LatLng

fun String?.toLatlng(): LatLng? {
    return try {
        if (this == null) {
            Log.d("toLatlng", "입력이 null임")
            return null
        }

        Log.d("toLatlng", "입력 문자열: '$this'")

        if (this.isBlank()) {
            Log.d("toLatlng", "입력이 빈 문자열임")
            return null
        }

        val parts = this.split(",")
        Log.d("toLatlng", "분할된 부분: $parts")

        if (parts.size != 2) {
            Log.e("toLatlng", "잘못된 형식: 쉼표로 분할된 부분이 2개가 아님")
            return null
        }

        val longitude = parts[0].trim().toDoubleOrNull()
        val latitude = parts[1].trim().toDoubleOrNull()

        Log.d("toLatlng", "경도: $longitude, 위도: $latitude")

        if (longitude == null || latitude == null) {
            Log.e("toLatlng", "숫자 변환 실패")
            return null
        }

        val result = LatLng(latitude, longitude)
        Log.d("toLatlng", "최종 결과: $result")
        return result

    } catch (e: Exception) {
        Log.e("toLatlng", "예외 발생: ${e.message}", e)
        null
    }
}