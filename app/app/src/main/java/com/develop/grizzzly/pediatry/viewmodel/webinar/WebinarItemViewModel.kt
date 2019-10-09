package com.develop.grizzzly.pediatry.viewmodel.webinar

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.fragments.WebinarsFragmentDirections
import com.develop.grizzzly.pediatry.network.model.Webinar
import java.text.SimpleDateFormat
import java.util.*

class WebinarItemViewModel(val data: MutableLiveData<Webinar>) : ViewModel() {

    fun getDateFormatted(): String {
        val formatter = SimpleDateFormat("dd.MM", Locale.US)
        return formatter.format(data.value?.startDate)
    }

    fun getMonth(): String {
        return when (data.value?.startDate?.month) {
            0 -> "ЯНВАРЯ"
            1 -> "ФЕВРАЛЯ"
            2 -> "МАРТА"
            3 -> "АПРЕЛЯ"
            4 -> "МАЯ"
            5 -> "ИЮНЯ"
            6 -> "ИЮЛЯ"
            7 -> "АВГУСТА"
            8 -> "СЕНТЯБРЯ"
            9 -> "ОКТЯБРЯ"
            10 -> "НОЯБРЯ"
            11 -> "ДЕКАБРЯ"
            else -> ""
        }
    }

    fun getTwoTimeDate(): String {
        val day = data.value?.startDate?.date
        if (day != null) {
            return if (day > 9) {
                Log.d("TAG", "$day")
                day.toString()
            } else {
                Log.d("TAG", "$day")
                "0$day"
            }
        }
        return ""
    }

    fun onWebinar(view: View) {
        val navController = Navigation.findNavController(view)

        val toWebinar = WebinarsFragmentDirections.actionWebinarToInfo()
        toWebinar.id = data.value?.id ?: 0L

        navController.navigate(toWebinar)

    }

}