package com.develop.grizzzly.pediatry.application

import android.app.Application
import com.develop.grizzzly.pediatry.BuildConfig

class ThisApp : Application() {

    companion object {
        lateinit var app: ThisApp
        val dev = BuildConfig.DEBUG
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }

}