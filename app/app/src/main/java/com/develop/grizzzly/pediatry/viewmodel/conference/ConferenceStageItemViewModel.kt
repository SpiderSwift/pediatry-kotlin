package com.develop.grizzzly.pediatry.viewmodel.conference

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.develop.grizzzly.pediatry.network.model.Program

class ConferenceStageItemViewModel(val data : MutableLiveData<Program>) : ViewModel() {
    fun getStartTime() : String = data.value!!.time.split("-")[0]
    fun getEndTime() : String = data.value!!.time.split("-")[1]

}