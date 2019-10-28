package com.develop.grizzzly.pediatry.db

import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.develop.grizzzly.pediatry.application.ThisApp

object DatabaseAccess {

    val database: AppDatabase by lazy {
        return@lazy Room.databaseBuilder(
            ThisApp.app,
            AppDatabase::class.java, "pediatry"
        )
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Log.w("DatabaseAccess", "onCreate: " + db.path)
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    Log.w("DatabaseAccess", "onOpen: " + db.path)
                }

                override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                    super.onDestructiveMigration(db)
                    Log.w("DatabaseAccess", "onDestructiveMigration: " + db.path)
                }
            })
            .build()
    }

}