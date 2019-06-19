package com.develop.grizzzly.pediatry.network.model

import java.util.*

class News  {
    var id : Long = 0
    var title : String = "Some title for news list"
    var announce : String = ""
    var date : Date = Date()
    var liked : Long = 150
    var likedByUsers : List<Long> = listOf()
}
