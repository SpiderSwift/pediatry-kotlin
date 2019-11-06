package com.develop.grizzzly.pediatry.viewmodel.module

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.develop.grizzzly.pediatry.network.model.Module
import com.develop.grizzzly.pediatry.network.model.Webinar
import com.develop.grizzzly.pediatry.util.TimeUtil

class ModulePostViewModel : ViewModel() {

    val data: MutableLiveData<Module> = MutableLiveData()

  //  private fun startTimeMs() : Long = data.value?.startTime?.toLong() ?: 0L

//    fun getMonth() : String = TimeUtil.printableMonth(startTimeMs())
//    fun getStartTimeHour(): String = TimeUtil.printableDayTime(startTimeMs())
//    fun getTwoTimeDate() : String = TimeUtil.printableMonthDay(startTimeMs())
//
//    fun getDetailDescription(): String {
//        val desc = data.value?.description
//        return if (desc != null && desc.isNotEmpty())
//            desc.toString()
//        else
//            "Описание отсутствует"
//    }

}