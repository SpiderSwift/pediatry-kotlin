package com.develop.grizzzly.pediatry.util

import android.text.Html
import android.widget.ImageView
import com.develop.grizzzly.pediatry.MainApplication
import com.develop.grizzzly.pediatry.network.WebAccess
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Authenticator
import okhttp3.Credentials
import okhttp3.OkHttpClient

fun setImage(path: String, imageView: ImageView, placeholderId: Int) {
    Picasso.get()
        .load(path)
        .fit()
        .centerCrop()
        .placeholder(placeholderId)
        .error(placeholderId)
        .into(imageView)

}

fun setAuthorizeMessage(path: String, imageView: ImageView) {
    val client = OkHttpClient.Builder()
        .authenticator { _, response ->
            val credential = Credentials.basic("m5edu_dev", "_p3Y3QPGuG")
            response.request().newBuilder()
                .header("Authorization", credential)
                .build()
        }
        .build()


    val picasso = Picasso.Builder(MainApplication.get()!!.baseContext).downloader(OkHttp3Downloader(client)).build()
    picasso.load(path)
        .fit()
        .centerCrop()
        //.placeholder(placeholderId)
        //.error(placeholderId)
        .into(imageView)
}

