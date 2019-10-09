package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Speciality constructor(
    @PrimaryKey val id: Long,
    val name: String,
    var main: Boolean = false
) {
    override fun toString(): String {
        return name
    }
}