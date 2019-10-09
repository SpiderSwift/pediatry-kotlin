package com.develop.grizzzly.pediatry.db

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(stamp: Long): Date? {
        return Date(stamp)
    }

    @TypeConverter
    fun fromLikedByUsers(liked: MutableList<Long>): String {
        return liked.joinToString()
    }

    @TypeConverter
    fun toLikedByUsers(data: String): MutableList<Long> {
        val list = data.split(", ")
        val longList = mutableListOf<Long>()
        list.forEach {
            if (it.isNotEmpty()) {
                try {
                    longList.add(it.toLong())
                } catch (e: Exception) {

                }
            }

        }
        return longList
    }

    @TypeConverter
    fun fromImageUrl(image_url: MutableList<String>): String {
        return image_url.joinToString()
    }

    @TypeConverter
    fun toImageUrl(data: String): MutableList<String> {
        val list = data.split(", ")
        val strList = mutableListOf<String>()
        list.forEach {
            if (it.isNotEmpty()) {
                try {
                    strList.add(it)
                } catch (e: Exception) {

                }
            }

        }
        return strList
    }

}