package com.develop.grizzzly.pediatry.application

import android.app.Application

class ThisApp : Application() {

    companion object {
        lateinit var app: ThisApp
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }

}