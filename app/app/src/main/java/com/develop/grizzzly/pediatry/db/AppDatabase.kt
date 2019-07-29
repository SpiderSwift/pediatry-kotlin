package com.develop.grizzzly.pediatry.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.develop.grizzzly.pediatry.db.dao.UserDao
import com.develop.grizzzly.pediatry.db.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}