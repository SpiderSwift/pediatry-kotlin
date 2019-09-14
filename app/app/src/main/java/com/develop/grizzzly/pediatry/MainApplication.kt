package com.develop.grizzzly.pediatry

import android.app.Application
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Credentials
import okhttp3.OkHttpClient


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this
    }

    val picasso : Picasso by lazy {
        val client = OkHttpClient.Builder()
            .authenticator { _, response ->
                val credential = Credentials.basic("m5edu_dev", "_p3Y3QPGuG")
                response.request().newBuilder()
                    .header("Authorization", credential)
                    .build()
            }
            .build()
        return@lazy Picasso.Builder(get()!!.baseContext).downloader(OkHttp3Downloader(client)).build()
    }

    companion object {
        private var instance: MainApplication? = null
        fun get(): MainApplication? {
            return instance
        }
    }
}