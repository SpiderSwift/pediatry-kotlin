package com.develop.grizzzly.pediatry.network

import com.develop.grizzzly.pediatry.network.model.AdsObject
import retrofit2.Response
import retrofit2.http.GET

interface AdApiClient {

    @GET("get_ads")
    suspend fun getAds(): Response<AdsObject>
}