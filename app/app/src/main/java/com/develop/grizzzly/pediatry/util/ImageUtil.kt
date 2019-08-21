package com.develop.grizzzly.pediatry.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.develop.grizzzly.pediatry.MainApplication


fun setImageGlide(path: String, imageView: ImageView, placeholderId: Int) {
    Glide.with(MainApplication.get()!!)
        .load(path)
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

