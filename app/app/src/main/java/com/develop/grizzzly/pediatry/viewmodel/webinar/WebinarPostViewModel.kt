package com.develop.grizzzly.pediatry.viewmodel.webinar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.develop.grizzzly.pediatry.network.model.Webinar
import com.develop.grizzzly.pediatry.util.TimeUtil
import java.util.*

class WebinarPostViewModel : ViewModel() {

    val data: MutableLiveData<Webinar> = MutableLiveData()

    fun startTime() : Long = data.value?.startTime?.toLong() ?: 0L

    fun getDetailDescription(): String {
        val desc = data.value?.description
        return if (desc != null && desc.isNotEmpty())
            desc.toString()
        else
            "Описание отсутствует"
    }

    fun getMonth() : String = TimeUtil.printableMonth(startTime()).toLowerCase(Locale.getDefault())

    fun getStartTimeHour(): String = TimeUtil.printableDayTime(startTime())

    fun getTwoTimeDate() : String = TimeUtil.printableMonthDay(startTime())

}