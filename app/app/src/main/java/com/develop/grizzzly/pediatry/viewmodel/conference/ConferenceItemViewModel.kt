package com.develop.grizzzly.pediatry.viewmodel.conference

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.extensions.navigateNoExcept
import com.develop.grizzzly.pediatry.fragments.ConferentionsFragmentDirections
import com.develop.grizzzly.pediatry.network.model.Conference
import com.develop.grizzzly.pediatry.util.TimeUtil

class ConferenceItemViewModel(val data: MutableLiveData<Conference>) : ViewModel() {

    private fun startTimeMs() : Long = data.value?.startDate?.time ?: 0L

    fun getMonth() : String = TimeUtil.printableMonth(startTimeMs(), false)
    fun getTwoTimeDate() : String = TimeUtil.printableMonthDay(startTimeMs())

    fun onConference(view: View) {
        val toStage = ConferentionsFragmentDirections.actionConferenceToStage()
        toStage.id = data.value!!.id!!
        Navigation.findNavController(view).navigateNoExcept(toStage)
    }

}