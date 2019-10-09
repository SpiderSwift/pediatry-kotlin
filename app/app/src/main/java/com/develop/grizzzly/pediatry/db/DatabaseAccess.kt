package com.develop.grizzzly.pediatry.db

import androidx.room.Room
import com.develop.grizzzly.pediatry.MainApplication

object DatabaseAccess {

    val database: AppDatabase by lazy {

        return@lazy Room.databaseBuilder(
            MainApplication.get()!!.applicationContext,
            AppDatabase::class.java, "pediatry"
        ).fallbackToDestructiveMigration().build()
    }

}