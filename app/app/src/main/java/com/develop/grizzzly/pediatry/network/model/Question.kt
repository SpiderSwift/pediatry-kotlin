package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.develop.grizzzly.pediatry.db.QuestionConverter

@Entity
data class Question constructor(
    @PrimaryKey
    val id: Long,
    val timeStamp: Long,
    val tags: String, //Todo
    @TypeConverters(QuestionConverter::class) var listAnswers: MutableList<Answer>,
    val textQuestion: String,
    val imageUrl: String,
    val correctAnswer: Int,
    val hintAnswerCount: Int //Todo
)