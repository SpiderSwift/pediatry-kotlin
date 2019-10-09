package com.develop.grizzzly.pediatry.network

import com.develop.grizzzly.pediatry.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*


object WebAccess {
    private val url = if (BuildConfig.DEBUG) {
        "https://edu-pediatrics.com/api/v1/"
    } else {
        "https://edu-pediatrics.com/api/v1/"
    }

    private val adUrl = "http://194.67.87.233/api/"


    var token: String = ""
    var id: Long = 0
    var offlineLog: Boolean = true

    val pediatryApi: PediatryApiClient by lazy {

        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request().newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                return@addInterceptor it.proceed(request)
            }
            .addInterceptor(loggingInterceptor)
            .build()

        val moshi = Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return@lazy retrofit.create(PediatryApiClient::class.java)
    }

    val adApi: AdApiClient by lazy {
        val client = OkHttpClient.Builder()
            .build()

        val moshi = Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(adUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return@lazy retrofit.create(AdApiClient::class.java)
    }
}