package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.develop.grizzzly.pediatry.db.DateConverter
import com.develop.grizzzly.pediatry.db.ListConverter
import java.util.*

@Entity
data class News constructor(@PrimaryKey val id : Long,
                            val title : String?,
                            var announce : String?,
                            @TypeConverters(DateConverter::class) val date : Date?,
                            val announcePicture : String?,
                            var liked : Long?,
                            @TypeConverters(ListConverter::class) var likedByUsers : MutableList<Long>)

