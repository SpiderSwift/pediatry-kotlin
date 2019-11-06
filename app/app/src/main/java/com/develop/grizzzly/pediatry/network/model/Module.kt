package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.develop.grizzzly.pediatry.db.MultiConverter
import com.google.gson.reflect.TypeToken

@Entity
data class Module(
    @PrimaryKey val id: Int,
    val title: String,
    val directionId: Int,
    val number: Int,
    val youtubeCode: String,
    val clinicalAnalysisDescription: String,
    val testStatus: Int,
    @TypeConverters(MultiConverter::class) val slides: MutableList<Slide>,
    @TypeConverters(MultiConverter::class) val articles: MutableList<Book>
) {
    companion object {
        val typeSlides = object : TypeToken<MutableList<Slide>>() {}.type!!
        val typeBooks = object : TypeToken<MutableList<Book>>() {}.type!!
    }
}