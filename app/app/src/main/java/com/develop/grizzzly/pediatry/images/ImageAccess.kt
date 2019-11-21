package com.develop.grizzzly.pediatry.images

import com.develop.grizzzly.pediatry.application.ThisApp
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Credentials
import okhttp3.OkHttpClient

object ImageAccess {

    val picasso: Picasso by lazy {
        val clientBuilder = OkHttpClient.Builder()
        if (true)  // TODO: replace with if (ThisApp.dev) when auth for prod ll be fixed
            clientBuilder
                .authenticator { _, response ->
                    response.request().newBuilder()
                        .header("Authorization", Credentials.basic("m5edu_dev", "_p3Y3QPGuG"))
                        .build()
                }
        return@lazy Picasso.Builder(ThisApp.app)
            .downloader(OkHttp3Downloader(clientBuilder.build()))
            .build()
    }

}