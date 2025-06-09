package com.mungkive.application.ui.feed.map

import NaverReverseGeocodeResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.Call

interface NaverReverseGeocodeApi {
    @GET("map-reversegeocode/v2/gc")
    @Headers(
        "X-NCP-APIGW-API-KEY-ID: uco22pbtd5",
        "X-NCP-APIGW-API-KEY: KYu6EHd2eZGCLp6TSpB1H3cnrKBsrzqXCq4girRU"
    )
    fun reverseGeocode(
        @Query("coords") coords: String,
        @Query("orders") orders: String = "legalcode,admcode,addr,roadaddr",
        @Query("output") output: String = "json"
    ): Call<NaverReverseGeocodeResponse>

}
