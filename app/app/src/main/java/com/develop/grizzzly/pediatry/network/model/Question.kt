package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question constructor(
    @PrimaryKey
    val id: Long,
    val timeStamp: Long,
    val tags: String, //Todo
    val listAnswers: ArrayList<Answer>?,
    val textQuestion: String?,
    val imageUrl: String?,
    val correctAnswer: Int,
    val hintAnswerCount: Int //Todo
)