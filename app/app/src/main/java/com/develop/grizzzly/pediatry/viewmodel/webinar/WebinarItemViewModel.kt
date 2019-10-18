package com.develop.grizzzly.pediatry.viewmodel.webinar

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.extensions.navigateNoExcept
import com.develop.grizzzly.pediatry.fragments.WebinarsFragmentDirections
import com.develop.grizzzly.pediatry.network.model.Webinar
import com.develop.grizzzly.pediatry.util.TimeUtil

class WebinarItemViewModel(val data: MutableLiveData<Webinar>) : ViewModel() {

    fun startTime() : Long = data.value?.startTime?.toLong() ?: 0L

    fun getMonth() : String = TimeUtil.printableMonth(startTime())

    fun getStartTimeHour(): String = TimeUtil.printableDayTime(startTime())

    fun getTwoTimeDate() : String = TimeUtil.printableMonthDay(startTime())

    fun onWebinar(view: View) {
        val toWebinar = WebinarsFragmentDirections.actionWebinarToInfo()
        toWebinar.id = data.value?.id ?: 0L
        Navigation.findNavController(view).navigateNoExcept(toWebinar)
    }

}