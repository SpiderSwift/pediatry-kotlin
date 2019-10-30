package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.develop.grizzzly.pediatry.db.MultiConverter
import com.google.gson.reflect.TypeToken

@Entity
data class Question constructor(
    @PrimaryKey
    val id: Long,
    val tsLastChange: Long,
    @TypeConverters(MultiConverter::class) val tags: MutableList<Int>,
    val imageUrl: String,
    val text: String,
    @TypeConverters(MultiConverter::class) val answers: MutableList<Answer>,
    @TypeConverters(MultiConverter::class) val correctAnswersCombo: Int,
    val hintAnswerCount: Int
) {
    companion object {
        val typeAnswers = object : TypeToken<MutableList<Answer>>() {}.type!!
        val typeInts = object : TypeToken<MutableList<Int>>() {}.type!!
    }
}