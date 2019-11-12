package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question(
    @PrimaryKey
    val id: String,
    val tsLastChange: Long,
    val tags: MutableList<Int>,
    val imageUrl: String,
    val text: String,
    val answers: MutableList<Answer>,
    val correctAnswersCombo: MutableList<Int>,
    val hintAnswerCount: Int
)