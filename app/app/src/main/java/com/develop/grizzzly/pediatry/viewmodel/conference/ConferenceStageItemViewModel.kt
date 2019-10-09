package com.develop.grizzzly.pediatry.viewmodel.conference

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.develop.grizzzly.pediatry.network.model.Program

class ConferenceStageItemViewModel(val data: MutableLiveData<Program>) : ViewModel() {
    fun getStartTime(): String {
        val list = data.value?.time?.split("-")
        return list!![0]
    }

    fun getEndTime(): String {
        val list = data.value?.time?.split("-")
        return if (list?.size == 1) {
            ""
        } else {
            list!![1]
        }
    }

}