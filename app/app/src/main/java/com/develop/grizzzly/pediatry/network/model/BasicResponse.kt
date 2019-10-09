package com.develop.grizzzly.pediatry.network.model

data class BasicResponse<T> constructor(
    val status: Long,
    val response: T?
)