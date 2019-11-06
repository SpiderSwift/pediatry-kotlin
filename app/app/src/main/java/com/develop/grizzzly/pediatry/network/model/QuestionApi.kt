package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.develop.grizzzly.pediatry.db.MultiConverter

@Entity
data class QuestionApi(
    @PrimaryKey
    val id: String,
    val tsLastChange: String,
    @TypeConverters(MultiConverter::class) val tags: MutableList<Int>,
    val imageUrl: String,
    val text: String,
    @TypeConverters(MultiConverter::class) val answers: MutableList<Answer>,
    @TypeConverters(MultiConverter::class) val correctAnswersCombo: MutableList<Int>,
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