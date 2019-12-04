package com.develop.grizzzly.pediatry.network.model

import android.util.Base64
import com.bumptech.glide.load.model.LazyHeaderFactory

data class BasicAuthorization(val name: String, val password: String) : LazyHeaderFactory {
    override fun buildHeader(): String? =
        "Basic " + Base64.encodeToString(("$name:$password").toByteArray(), Base64.NO_WRAP)
}