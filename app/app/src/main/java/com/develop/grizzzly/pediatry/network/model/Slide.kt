package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity

@Entity
data class Slide(
    val number: Int,
    val title: String,
    val image: String
)