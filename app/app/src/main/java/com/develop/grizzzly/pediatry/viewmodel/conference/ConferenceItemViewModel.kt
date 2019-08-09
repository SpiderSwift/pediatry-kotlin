package com.develop.grizzzly.pediatry.viewmodel.conference


import androidx.lifecycle.MutableLiveData
import com.develop.grizzzly.pediatry.network.model.Conference
import java.text.SimpleDateFormat
import java.util.*

class ConferenceItemViewModel(val data : MutableLiveData<Conference>) {


    fun getDateFormatted() : String {
        val formatter = SimpleDateFormat("dd.MM", Locale.US)
        return formatter.format(data.value?.startDate)
    }

}