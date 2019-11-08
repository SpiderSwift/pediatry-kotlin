package com.develop.grizzzly.pediatry.db

import androidx.room.TypeConverter
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.Answer
import com.google.gson.reflect.TypeToken
import java.util.*

class MultiConverter {

    companion object {
        val typeAnswers = object : TypeToken<MutableList<Answer>>() {}.type!!
        val typeInts = object : TypeToken<MutableList<Int>>() {}.type!!
        val typeLongs = object : TypeToken<MutableList<Long>>() {}.type!!
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(stamp: Long): Date? {
        return Date(stamp)
    }

    @TypeConverter
    fun fromLongs(liked: MutableList<Long>): String {
        return WebAccess.gson.toJson(liked).toString()
    }

    @TypeConverter
    fun toLongs(data: String): MutableList<Long> {
        return WebAccess.gson.fromJson<MutableList<Long>>(data, typeLongs)
    }

    @TypeConverter
    fun fromAnswers(list: MutableList<Answer>): String {
        return WebAccess.gson.toJson(list).toString()
    }

    @TypeConverter
    fun toAnswers(str: String): MutableList<Answer> {
        return WebAccess.gson.fromJson<MutableList<Answer>>(str, typeAnswers)
    }

    @TypeConverter
    fun fromInts(list: MutableList<Int>): String {
        return WebAccess.gson.toJson(list).toString()
    }

    @TypeConverter
    fun toInts(str: String): MutableList<Int> {
        return WebAccess.gson.fromJson<MutableList<Int>>(str, typeInts)
    }

}