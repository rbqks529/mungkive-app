package com.mungkive.application.ui.feed.map

import NaverReverseGeocodeResponse
import android.content.Context
import android.location.Geocoder
import android.util.Log
import java.util.Locale
import retrofit2.Call

fun getPlaceNameFromLocation(
    context: Context,
    longitude: Double,
    latitude: Double,
    onResult: (String) -> Unit
) {
    val TAG = "LocationHelper"
    val coords = "$longitude,$latitude"
    val call = RetrofitNaverMap.naverApi.reverseGeocode(coords)

    Log.d(TAG, "네이버 API 요청 시작: coords=$coords")

    call.enqueue(object : retrofit2.Callback<NaverReverseGeocodeResponse> {
        override fun onResponse(
            call: Call<NaverReverseGeocodeResponse>,
            response: retrofit2.Response<NaverReverseGeocodeResponse>
        ) {
            Log.d(TAG, "네이버 API 응답 수신됨: isSuccessful=${response.isSuccessful}")
            val body = response.body()
            if (body != null) {
                Log.d(TAG, "응답 내용: $body")
            } else {
                Log.e(TAG, "${response.errorBody()?.string()}")
            }

            val result = body?.results?.firstOrNull()
            val fullAddress = listOfNotNull(
                result?.region?.area1?.name, // 시/도
                result?.region?.area2?.name, // 시/군/구
                result?.region?.area3?.name, // 읍/면/동
                result?.land?.name,          // 도로명
                result?.land?.number1?.takeIf { it.isNotBlank() }?.let {
                    if (!result.land?.number2.isNullOrBlank()) "$it-${result.land?.number2}" else it
                }
            ).joinToString(" ")

            if (fullAddress.isNotBlank()) {
                Log.d(TAG, "전체 주소 추출 성공: $fullAddress")
                onResult(fullAddress)
            } else {
                Log.w(TAG, "주소 정보 부족, Geocoder fallback 실행")
                try {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                    if (!addresses.isNullOrEmpty()) {
                        val addr = addresses[0]
                        val addressStr = listOfNotNull(
                            addr.adminArea,
                            addr.locality,
                            addr.thoroughfare
                        ).joinToString(" ")
                        Log.d(TAG, "Geocoder 주소 변환 성공: $addressStr")
                        onResult(addressStr.ifBlank { "위치 정보 없음" })
                    } else {
                        Log.e(TAG, "Geocoder 결과 없음")
                        onResult("위치 정보 없음")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Geocoder 예외 발생: ${e.message}", e)
                    onResult("위치 정보 없음")
                }
            }
        }

        override fun onFailure(call: Call<NaverReverseGeocodeResponse>, t: Throwable) {
            Log.e(TAG, "네이버 API 호출 실패: ${t.message}", t)
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val addr = addresses[0]
                    val addressStr = listOfNotNull(
                        addr.adminArea,
                        addr.locality,
                        addr.thoroughfare
                    ).joinToString(" ")
                    Log.d(TAG, "Geocoder 주소 변환 성공 (fallback): $addressStr")
                    onResult(addressStr.ifBlank { "위치 정보 없음" })
                } else {
                    Log.e(TAG, "Geocoder 결과 없음 (fallback)")
                    onResult("위치 정보 없음")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Geocoder 예외 발생 (fallback): ${e.message}", e)
                onResult("위치 정보 없음")
            }
        }
    })
}
