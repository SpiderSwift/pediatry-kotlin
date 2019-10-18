package com.develop.grizzzly.pediatry.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {
    
    fun printableMonth(timeInMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
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

    fun printableDayTime(timeInMillis: Long): String {
        return SimpleDateFormat("hh:mm", Locale.US).format(Date(timeInMillis))
    }

    fun printableMonthDay(timeInMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        return "%02d".format(calendar.get(Calendar.DAY_OF_MONTH))
    }

}