package com.develop.grizzzly.pediatry.util

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun setImage(path: String, imageView: ImageView, placeholderId: Int) {
    Picasso.get()
        .load(path)
        .fit()
        .centerCrop()
        .placeholder(placeholderId)
        .error(placeholderId)
        .into(imageView)


}