package com.mungkive.application.ui.feed.retrofit

import android.content.Context
import com.mungkive.application.ui.feed.retrofit.RetrofitNaverMap.naverApi
import retrofit2.Call

import android.location.Geocoder
import java.util.Locale

fun getPlaceNameFromLocation(
    context: Context,
    longitude: Double,
    latitude: Double,
    onResult: (String) -> Unit
) {
    val coords = "$longitude,$latitude"
    val call = naverApi.reverseGeocode(coords)
    call.enqueue(object : retrofit2.Callback<NaverReverseGeocodeResponse> {
        override fun onResponse(
            call: Call<NaverReverseGeocodeResponse>,
            response: retrofit2.Response<NaverReverseGeocodeResponse>
        ) {
            val body = response.body()
            val landName = body?.results?.firstOrNull { it.land?.name != null }?.land?.name
            val regionName = body?.results?.firstOrNull()?.region?.area3?.name
            // 1. 네이버 API에서 장소명 우선 반환
            val poi = landName ?: regionName
            if (!poi.isNullOrBlank() && poi != "장소명 없음" && poi != "장소명 변환 실패") {
                onResult(poi)
            } else {
                // 2. 장소명 실패시 Geocoder로 주소 변환 시도
                try {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                    if (!addresses.isNullOrEmpty()) {
                        val addr = addresses[0]
                        val addressStr = listOfNotNull(
                            addr.adminArea,   // 시/도
                            addr.locality,    // 시/군/구
                            addr.thoroughfare // 도로명
                        ).joinToString(" ")
                        onResult(addressStr.ifBlank { "위치 정보 없음" })
                    } else {
                        onResult("위치 정보 없음")
                    }
                } catch (e: Exception) {
                    onResult("위치 정보 없음")
                }
            }
        }
        override fun onFailure(call: Call<NaverReverseGeocodeResponse>, t: Throwable) {
            // 네이버 API 통신 자체가 실패하면 바로 Geocoder로 시도
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val addr = addresses[0]
                    val addressStr = listOfNotNull(
                        addr.adminArea,   // 시/도
                        addr.locality,    // 시/군/구
                        addr.thoroughfare // 도로명
                    ).joinToString(" ")
                    onResult(addressStr.ifBlank { "위치 정보 없음" })
                } else {
                    onResult("위치 정보 없음")
                }
            } catch (e: Exception) {
                onResult("위치 정보 없음")
            }
        }
    })
}

