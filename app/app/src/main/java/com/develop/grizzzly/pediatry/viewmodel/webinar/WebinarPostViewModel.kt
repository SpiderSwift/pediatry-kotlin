package com.develop.grizzzly.pediatry.viewmodel.webinar


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.develop.grizzzly.pediatry.network.model.Webinar
import java.text.SimpleDateFormat
import java.util.*

class WebinarPostViewModel : ViewModel() {

    val data: MutableLiveData<Webinar> = MutableLiveData()

    fun getDetailDescription(): String {
        val desc = data.value?.description
        if (desc != null) {
            return if (desc.isNotEmpty()) {
                desc.toString()
            } else {
                "Описание отсутствует"
            }
        }
        return "Описание отсутствует"
    }

    fun getMonth() : String {
        val date = Date(data.value?.startTime?.toLong() ?: 0L)
        return when (date.month) {
            0 -> "января"
            1 -> "февраля"
            2 -> "марта"
            3 -> "апреля"
            4 -> "мая"
            5 -> "июня"
            6 -> "июля"
            7 -> "августа"
            8 -> "сентября"
            9 -> "октября"
            10 -> "ноября"
            11 -> "декабря"
            else -> ""
        }
    }


    fun getStartTimeHour() : String {
        val date = Date(data.value?.startTime?.toLong() ?: 0L)
        val formatter = SimpleDateFormat("hh:mm", Locale.US)
        return formatter.format(date)
    }

    fun getTwoTimeDate() : String {
        val date = Date(data.value?.startTime?.toLong() ?: 0L)
        val day = date.date
        return if (day > 9) {
            Log.d("TAG", "$day")
            day.toString()
        } else {
            Log.d("TAG", "$day")
            "0$day"
        }
    }

}