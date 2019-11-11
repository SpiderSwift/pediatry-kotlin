package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.develop.grizzzly.pediatry.db.MultiConverter

@Entity
data class Question(
    @PrimaryKey
    val id: String,
    val tsLastChange: Long,
    @TypeConverters(MultiConverter::class) val tags: MutableList<Int>,
    val imageUrl: String,
    val text: String,
    @TypeConverters(MultiConverter::class) val answers: MutableList<Answer>,
    @TypeConverters(MultiConverter::class) val correctAnswersCombo: MutableList<Int>,
    val hintAnswerCount: Int
)