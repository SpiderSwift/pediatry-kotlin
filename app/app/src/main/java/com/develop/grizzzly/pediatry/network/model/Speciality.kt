package com.develop.grizzzly.pediatry.network.model

data class Speciality constructor(val id : Long,
                                  val name : String) {
    override fun toString(): String {
        return name
    }
}