package com.develop.grizzzly.pediatry.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.develop.grizzzly.pediatry.db.dao.ConferenceDao
import com.develop.grizzzly.pediatry.db.dao.NewsDao
import com.develop.grizzzly.pediatry.db.dao.ProfileDao
import com.develop.grizzzly.pediatry.db.dao.UserDao
import com.develop.grizzzly.pediatry.db.model.User
import com.develop.grizzzly.pediatry.network.model.Conference
import com.develop.grizzzly.pediatry.network.model.News
import com.develop.grizzzly.pediatry.network.model.Profile

@Database(entities = [User::class, News::class, Conference::class, Profile::class], version = 4)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun newsDao(): NewsDao
    abstract fun conferenceDao() : ConferenceDao
    abstract fun profileDao() : ProfileDao
}