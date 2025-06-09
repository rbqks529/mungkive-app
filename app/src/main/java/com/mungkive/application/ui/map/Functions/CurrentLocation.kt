package com.mungkive.application.ui.map.Functions

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng

//@SuppressLint("MissingPermission")
//suspend fun getCurrentLatLng(context: Context): LatLng? {
//    val fusedLocationClient: FusedLocationProviderClient =
//        LocationServices.getFusedLocationProviderClient(context)
//    val location: Location? = fusedLocationClient.lastLocation.await()
//    return location?.let { LatLng(it.latitude, it.longitude) }
//}