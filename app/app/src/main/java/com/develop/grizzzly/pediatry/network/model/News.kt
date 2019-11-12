package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class News constructor(
    @PrimaryKey val id: Long,
    val title: String?,
    val announce: String?,
    val date: Date?,
    val announcePicture: String?,
    var liked: Long?,
    var likedByUsers: MutableList<Long>,
    val isAd: Boolean = false,
    val adVideoUrl: String?,
    val attachedUrl: String?
)

