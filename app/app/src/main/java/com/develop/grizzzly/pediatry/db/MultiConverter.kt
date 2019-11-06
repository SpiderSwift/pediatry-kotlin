package com.develop.grizzzly.pediatry.db

import androidx.room.TypeConverter
import com.develop.grizzzly.pediatry.network.model.*
import com.google.gson.Gson
import java.util.*

class MultiConverter {

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
                    e.printStackTrace()
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
                    e.printStackTrace()
                }
            }

        }
        return strList
    }

    @TypeConverter
    fun fromAnswer(list: MutableList<Answer>): String {
        return Gson().toJson(list).toString()
    }

    @TypeConverter
    fun toAnswer(str: String): MutableList<Answer> {
        return Gson().fromJson<MutableList<Answer>>(str, Question.typeAnswers)
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
        return Gson().toJson(list).toString()
    }

    @TypeConverter
    fun toInts(str: String): MutableList<Int> {
        return Gson().fromJson<MutableList<Int>>(str, Question.typeInts)
    }
}