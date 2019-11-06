package com.develop.grizzzly.pediatry.viewmodel.module

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.extensions.navigateNoExcept
import com.develop.grizzzly.pediatry.fragments.WebinarsFragmentDirections
import com.develop.grizzzly.pediatry.network.model.Module
import com.develop.grizzzly.pediatry.network.model.Webinar
import com.develop.grizzzly.pediatry.util.TimeUtil

class ModuleItemViewModel(val data: MutableLiveData<Module>) : ViewModel() {

    //private fun startTimeMs() : Long = data.value?.startTime?.toLong() ?: 0L

//    fun getMonth() : String = TimeUtil.printableMonth(startTimeMs(), false)
//    fun getStartTimeHour(): String = TimeUtil.printableDayTime(startTimeMs())
//    fun getTwoTimeDate() : String = TimeUtil.printableMonthDay(startTimeMs())
//
//    fun onWebinar(view: View) {
//        val toModule = ModulesFragmentDirections.actionWebinarToInfo()
//        toModule.id = data.value?.id ?: 0L
//        Navigation.findNavController(view).navigateNoExcept(toModule)
//    }

}