package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Ad constructor(
    @PrimaryKey
    val id: Long,
    val title: String,
    var image_url: String,
    val show_image: Boolean,
    val video_url: String?
) {
    fun toNews(): News {
        return News(id, title, "", Date(), image_url, 0, mutableListOf(), true)
    }
}