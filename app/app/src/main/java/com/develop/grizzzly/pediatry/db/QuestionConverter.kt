package com.develop.grizzzly.pediatry.db

import android.util.Log
import androidx.room.TypeConverter
import com.develop.grizzzly.pediatry.network.model.Answer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class QuestionConverter {

    @TypeConverter
    fun fromAnswer(list: MutableList<Answer>): String {
        return Gson().toJson(list).toString()
    }

    @TypeConverter
    fun toAnswer(str: String): MutableList<Answer> {
        val listType = object : TypeToken<MutableList<Answer>>() {}.type
        val newList: MutableList<Answer> = Gson().fromJson<MutableList<Answer>>(str, listType)
        Log.println(Log.ASSERT, "msg: ", newList.toString())
        return newList
    }
}