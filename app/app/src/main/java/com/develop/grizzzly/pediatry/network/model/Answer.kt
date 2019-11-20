package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity

@Entity
data class Answer constructor(var num: Int, var text: String)