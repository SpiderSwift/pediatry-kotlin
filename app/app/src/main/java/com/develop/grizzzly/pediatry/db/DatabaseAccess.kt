package com.develop.grizzzly.pediatry.db

import androidx.room.Room
import com.develop.grizzzly.pediatry.application.ThisApp

object DatabaseAccess {

    val database: AppDatabase by lazy {
        return@lazy Room.databaseBuilder(
            ThisApp.app,
            AppDatabase::class.java, "pediatry"
        ).fallbackToDestructiveMigration().build()
    }

}