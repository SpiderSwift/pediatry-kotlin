package com.develop.grizzzly.pediatry.network

import com.develop.grizzzly.pediatry.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

import java.util.*

    const val DEV_URL = "https://dev.edu-pediatrics.com/api/v1/"
const val PROD_URL = "https://edu-pediatrics.com/api/v1/"

object WebAccess {

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
        /**
         * @param BuildConfig.BASE_URL прописан в файле build.gradle для каждой переменной
         */
        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return@lazy retrofit.create(PediatryApiClient::class.java)
    }
}