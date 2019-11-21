package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question constructor(
    @PrimaryKey
    val id: String,
    val tsLastChange: Long,
    val tags: MutableList<Int>,
    val text: String,
    val answers: MutableList<Answer>,
    val correctAnswersCombo: MutableList<Int>,
    val hintAnswerCount: Int
)