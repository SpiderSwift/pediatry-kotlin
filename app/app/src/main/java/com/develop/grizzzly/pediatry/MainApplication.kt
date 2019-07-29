package com.develop.grizzzly.pediatry

import android.app.Application


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private var instance: MainApplication? = null
        fun get(): MainApplication? {
            return instance
        }
    }
}