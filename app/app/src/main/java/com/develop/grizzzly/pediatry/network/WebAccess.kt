package com.develop.grizzzly.pediatry.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

import java.util.*


object WebAccess {

    const val url = "https://edu-pediatrics.com/api/v1/"
    var token : String = ""
    var id : Long = 0

    val pediatryApi : PediatryApiClient by lazy {

        val client = OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request().newBuilder().addHeader("Authorization", "Bearer $token").build()
                return@addInterceptor it.proceed(request)
            }
            .build()

        val moshi = Moshi.Builder()
            .add(Date::class.java ,Rfc3339DateJsonAdapter())
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return@lazy retrofit.create(PediatryApiClient::class.java)
    }


}