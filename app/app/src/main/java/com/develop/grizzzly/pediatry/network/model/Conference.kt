package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Conference constructor(
    @PrimaryKey val id: Long?,
    val title: String?,
    val description: String?,
    val startDate: Date?,
    val startTime: String?,
    val city: String?,
    val address: String?,
    val onlineScore: Long?,
    val offlineScore: Long?
)



