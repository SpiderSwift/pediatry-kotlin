package com.develop.grizzzly.pediatry.viewmodel.webinar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.develop.grizzzly.pediatry.network.model.Webinar

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

    fun getMonth(): String {
        return when (data.value?.startDate?.month) {
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

    fun getTwoTimeDate(): String {
        val day = data.value?.startDate?.date
        if (day != null) {
            return if (day > 9) {
                day.toString()
            } else {
                "$day"
            }
        }
        return ""
    }

}