package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuestionApi(
    @PrimaryKey
    val id: String,
    val tsLastChange: String,
    val tags: MutableList<Int>,
    val imageUrl: String,
    val text: String,
    val answers: MutableList<Answer>,
    val correctAnswersCombo: MutableList<Int>,
    val hintAnswerCount: Int
) {
    fun convert(): Question {
        for (ans in answers)
            ans.text = ans.text.trim()
        return Question(
            id,
            tsLastChange.toLong(),
            tags,
            imageUrl,
            text,
            answers,
            correctAnswersCombo,
            hintAnswerCount
        )
    }
}