package com.develop.grizzzly.pediatry.network

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object WebAccess {

    private const val url = "https://dev.edu-pediatrics.com/api/v1/"

    val pediatryApi : PediatryApiClient by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
        return@lazy retrofit.create(PediatryApiClient::class.java)
    }
}