package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.develop.grizzzly.pediatry.db.MultiConverter
import java.util.*

@Entity
data class News constructor(
    @PrimaryKey val id: Long,
    val title: String?,
    val announce: String?,
    @TypeConverters(MultiConverter::class) val date: Date?,
    val announcePicture: String?,
    var liked: Long?,
    @TypeConverters(MultiConverter::class) var likedByUsers: MutableList<Long>,
    val isAd: Boolean = false,
    val adVideoUrl: String?,
    val attachedUrl: String?
)

