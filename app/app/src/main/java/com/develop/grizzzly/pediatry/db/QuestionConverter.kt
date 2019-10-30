package com.develop.grizzzly.pediatry.db

import androidx.room.TypeConverter
import com.develop.grizzzly.pediatry.network.model.Answer

class QuestionConverter {

    @TypeConverter
    fun fromAnswer(list: MutableList<Answer>): String {
        return list.joinToString()
    }

    @TypeConverter
    fun toAnswer(str: String): MutableList<Answer> {
        val list = str.split(", ")
        val answerList = mutableListOf<Answer>()
        list.forEach {
            if (it.isNotEmpty()) {
                try {
                    answerList.add(Answer(0,"test"))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return answerList
    }

}