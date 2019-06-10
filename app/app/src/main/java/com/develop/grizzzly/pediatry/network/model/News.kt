package com.develop.grizzzly.pediatry.network.model

import java.util.*

data class News constructor(val id : Long, val title : String, val announce : String, val date : Date, val liked : Long, val likedByUsers : List<Long>)