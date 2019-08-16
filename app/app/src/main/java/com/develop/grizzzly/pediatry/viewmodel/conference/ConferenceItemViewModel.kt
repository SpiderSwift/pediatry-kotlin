package com.develop.grizzzly.pediatry.viewmodel.conference


import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.network.model.Conference
import java.text.SimpleDateFormat
import java.util.*

class ConferenceItemViewModel(val data : MutableLiveData<Conference>) : ViewModel() {


    fun getDateFormatted() : String {
        val formatter = SimpleDateFormat("dd.MM", Locale.US)
        return formatter.format(data.value?.startDate)
    }


    fun onConference(view : View) {
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_conference_to_stage)
    }

}