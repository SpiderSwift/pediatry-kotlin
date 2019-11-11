package com.develop.grizzzly.pediatry.db

import androidx.room.TypeConverter
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.Answer
import com.develop.grizzzly.pediatry.network.model.Book
import com.develop.grizzzly.pediatry.network.model.Module
import com.develop.grizzzly.pediatry.network.model.Slide
import com.google.gson.Gson
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
    fun fromSlide(list: MutableList<Slide>): String {
        return Gson().toJson(list).toString()
    }

    @TypeConverter
    fun toSlider(str: String): MutableList<Slide> {
        return Gson().fromJson<MutableList<Slide>>(str, Module.typeSlides)
    }

    @TypeConverter
    fun fromBook(list: MutableList<Book>): String {
        return Gson().toJson(list).toString()
    }

    @TypeConverter
    fun toBook(str: String): MutableList<Book> {
        return Gson().fromJson<MutableList<Book>>(str, Module.typeBooks)
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