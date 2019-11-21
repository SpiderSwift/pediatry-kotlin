package com.develop.grizzzly.pediatry.db

import androidx.room.TypeConverter
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.Answer
import com.develop.grizzzly.pediatry.network.model.Article
import com.develop.grizzzly.pediatry.network.model.Slide
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import java.util.*

object MultiConverter {

    private val answerListAdapter: JsonAdapter<MutableList<Answer>> =
        WebAccess.moshi.adapter<MutableList<Answer>>(
            Types.newParameterizedType(MutableList::class.java, Answer::class.java)
        )
    private val intListAdapter: JsonAdapter<MutableList<Int>> =
        WebAccess.moshi.adapter<MutableList<Int>>(
            Types.newParameterizedType(MutableList::class.java, Int::class.javaObjectType)
        )
    private val longListAdapter: JsonAdapter<MutableList<Long>> =
        WebAccess.moshi.adapter<MutableList<Long>>(
            Types.newParameterizedType(MutableList::class.java, Long::class.javaObjectType)
        )
    private val slideListAdapter: JsonAdapter<MutableList<Slide>> =
        WebAccess.moshi.adapter<MutableList<Slide>>(
            Types.newParameterizedType(MutableList::class.java, Slide::class.java)
        )
    private val bookListAdapter: JsonAdapter<MutableList<Article>> =
        WebAccess.moshi.adapter<MutableList<Article>>(
            Types.newParameterizedType(MutableList::class.java, Article::class.java)
        )

    @TypeConverter
    @JvmStatic
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    @JvmStatic
    fun toDate(stamp: Long): Date? {
        return Date(stamp)
    }

    @TypeConverter
    @JvmStatic
    fun fromLongs(list: MutableList<Long>): String {
        return longListAdapter.toJson(list)
    }

    @TypeConverter
    @JvmStatic
    fun toLongs(str: String): MutableList<Long> {
        return longListAdapter.fromJson(str)!!
    }

    @TypeConverter
    @JvmStatic
    fun fromAnswers(list: MutableList<Answer>): String {
        return answerListAdapter.toJson(list)
    }

    @TypeConverter
    @JvmStatic
    fun toAnswers(str: String): MutableList<Answer> {
        return answerListAdapter.fromJson(str)!!
    }

    @TypeConverter
    @JvmStatic
    fun fromSlides(list: MutableList<Slide>): String {
        return slideListAdapter.toJson(list)
    }

    @TypeConverter
    @JvmStatic
    fun toSlides(str: String): MutableList<Slide> {
        return slideListAdapter.fromJson(str)!!
    }

    @TypeConverter
    @JvmStatic
    fun fromBooks(list: MutableList<Article>): String {
        return bookListAdapter.toJson(list)
    }

    @TypeConverter
    @JvmStatic
    fun toBooks(str: String): MutableList<Article> {
        return bookListAdapter.fromJson(str)!!
    }

    @TypeConverter
    @JvmStatic
    fun fromInts(list: MutableList<Int>): String {
        return intListAdapter.toJson(list)
    }

    @TypeConverter
    @JvmStatic
    fun toInts(str: String): MutableList<Int> {
        return intListAdapter.fromJson(str)!!
    }

}