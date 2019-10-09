package com.develop.grizzzly.pediatry.network.model

import java.util.*

data class Broadcast constructor(
    val id: Long,
    val title: String,
    val subTitle: String?,
    val description: String,
    val startDate: Date,
    val startTime: String,
    val endTime: String,
    val score: Long?,
    val confirmationTime1: String?,
    val confirmationTime2: String?,
    val confirmationTime3: String?,
    val youtubeCode: String?,
    val email: String?
)


