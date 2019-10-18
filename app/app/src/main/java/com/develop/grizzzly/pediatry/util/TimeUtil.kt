package com.develop.grizzzly.pediatry.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

    fun printableMonth(timeInMillis: Long, isLowerCase: Boolean = true): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        val month = when (calendar.get(Calendar.MONTH)) {
            0 -> "января"
            1 -> "февраля"
            2 -> "марта"
            3 -> "апреля"
            4 -> "мая"
            5 -> "июня"
            6 -> "июля"
            7 -> "августа"
            8 -> "сентября"
            9 -> "октября"
            10 -> "ноября"
            11 -> "декабря"
            else -> ""
        }
        return if (isLowerCase) month
        else month.toLowerCase(Locale.getDefault())
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