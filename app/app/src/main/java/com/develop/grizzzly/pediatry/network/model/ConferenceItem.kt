package com.develop.grizzzly.pediatry.network.model

data class ConferenceItem constructor(
    val id: Long,
    val title: String,
    val description: String,
    val startDate: String,
    val startTime: String,
    val city: String,
    val address: String,
    val onlineScore: Long,
    val offlineScore: Long,
    var isRegistered: Boolean,
    val programs: List<Program>
)