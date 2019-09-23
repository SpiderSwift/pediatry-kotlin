package com.develop.grizzzly.pediatry.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.develop.grizzzly.pediatry.db.dao.*
import com.develop.grizzzly.pediatry.db.model.User
import com.develop.grizzzly.pediatry.network.model.Conference
import com.develop.grizzzly.pediatry.network.model.News
import com.develop.grizzzly.pediatry.network.model.Profile
import com.develop.grizzzly.pediatry.network.model.Speciality

@Database(entities = [User::class, News::class, Conference::class, Profile::class, Speciality::class], version = 7)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun newsDao(): NewsDao
    abstract fun conferenceDao() : ConferenceDao
    abstract fun profileDao() : ProfileDao
    abstract fun specialityDao() : SpecialityDao
}