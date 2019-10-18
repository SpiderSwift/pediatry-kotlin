package com.develop.grizzzly.pediatry.viewmodel.webinar

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.extensions.navigateNoExcept
import com.develop.grizzzly.pediatry.fragments.WebinarsFragmentDirections
import com.develop.grizzzly.pediatry.network.model.Webinar
import java.text.SimpleDateFormat
import java.util.*

class WebinarItemViewModel(val data: MutableLiveData<Webinar>) : ViewModel() {

    fun startTime() : Long = data.value?.startTime?.toLong() ?: 0L

    fun getMonth() : String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = startTime()
        return when (calendar.get(Calendar.MONTH)) {
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

    fun getStartTimeHour(): String {
        return SimpleDateFormat("hh:mm", Locale.US).format(Date(startTime()))
    }

    fun getTwoTimeDate() : String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = startTime()
        return "%02d".format(calendar.get(Calendar.DAY_OF_MONTH))
    }

    fun onWebinar(view: View) {
        val toWebinar = WebinarsFragmentDirections.actionWebinarToInfo()
        toWebinar.id = data.value?.id ?: 0L
        Navigation.findNavController(view).navigateNoExcept(toWebinar)
    }

}