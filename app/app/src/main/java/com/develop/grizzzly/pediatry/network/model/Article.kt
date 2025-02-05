package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity

@Entity
data class Article(val title: String, val author: String, val category: String?, val file: String)