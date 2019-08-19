package com.develop.grizzzly.pediatry.network.model

import java.util.*

data class News constructor(val id : Long,
                            val title : String?,
                            var announce : String?,
                            val date : Date?,
                            val announcePicture : String?,
                            var liked : Long?,
                            var likedByUsers : MutableList<Long>)

