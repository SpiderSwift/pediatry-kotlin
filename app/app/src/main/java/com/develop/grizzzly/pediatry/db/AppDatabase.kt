package com.develop.grizzzly.pediatry.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.develop.grizzzly.pediatry.db.dao.*
import com.develop.grizzzly.pediatry.db.model.User
import com.develop.grizzzly.pediatry.network.model.*

@Database(entities = [User::class, News::class, Conference::class, Profile::class, Speciality::class, Webinar::class], version = 12)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun newsDao(): NewsDao
    abstract fun conferenceDao() : ConferenceDao
    abstract fun profileDao() : ProfileDao
    abstract fun specialityDao() : SpecialityDao
    abstract fun webinarDao() : WebinarDao
}