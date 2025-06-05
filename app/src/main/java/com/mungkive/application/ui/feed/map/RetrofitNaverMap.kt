package com.mungkive.application.ui.feed.map

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitNaverMap {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://naveropenapi.apigw.ntruss.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val naverApi = retrofit.create(NaverReverseGeocodeApi::class.java)
}