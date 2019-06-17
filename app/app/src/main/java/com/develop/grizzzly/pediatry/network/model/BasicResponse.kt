package com.develop.grizzzly.pediatry.network.model

class BasicResponse {
    var status : Long = 0
    var response : List<News> = listOf()
    var message : Any = Any()
    override fun toString(): String {
        return "BasicResponse(status=$status, response=$response, message=$message)"
    }


}