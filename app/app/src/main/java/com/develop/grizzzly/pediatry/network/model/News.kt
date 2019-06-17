package com.develop.grizzzly.pediatry.network.model

import java.util.*

class News  {
    var id : Long = 0
    var title : String = ""
    var announce : String = ""
    var date : Date = Date()
    var liked : Long = 0
    var likedByUsers : List<Long> = listOf()
}
