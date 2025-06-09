package com.mungkive.application.ui.map.Functions

import com.naver.maps.geometry.LatLng

fun String.toLatlng(): LatLng? {
    val arr = this.split(",")
    return if(arr.size == 2){
        val lng = arr[0].trim().toDoubleOrNull()
        val lat = arr[1].trim().toDoubleOrNull()
        if(lat != null && lng != null){
            LatLng(lat,lng)
        }else null
    }else null
}