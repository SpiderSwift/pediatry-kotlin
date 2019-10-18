package com.develop.grizzzly.pediatry.ui

import com.develop.grizzzly.pediatry.application.ThisApp
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Credentials
import okhttp3.OkHttpClient

object ImageAccess {

    val picasso: Picasso by lazy {
        val client = OkHttpClient.Builder()
            .authenticator { _, response ->
                val credential = Credentials.basic("m5edu_dev", "_p3Y3QPGuG")
                response.request().newBuilder()
                    .header("Authorization", credential)
                    .build()
            }
            .build()
        return@lazy Picasso.Builder(ThisApp.app)
            .downloader(OkHttp3Downloader(client))
            .build()
    }

}