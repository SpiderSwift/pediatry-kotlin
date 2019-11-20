package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Module(
    @PrimaryKey val id: Int,
    val title: String,
    val directionId: Int,
    val number: Int,
    val youtubeCode: String?,
    val clinicalAnalysisDescription: String,
    val testStatus: Int
)