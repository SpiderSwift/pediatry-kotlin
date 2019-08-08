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

fun setAuthorizeMessage(path: String, imageView: ImageView, placeholderId: Int) {

    val picasso = MainApplication.get()!!.picasso
    picasso.load(path)
        .fit()
        .centerCrop()
        .placeholder(placeholderId)
        .error(placeholderId)
        .into(imageView)
}

