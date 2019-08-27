package com.develop.grizzzly.pediatry.network.model

data class ConferenceItem constructor(val id : Long,
                          val title : String,
                          val description : String,
                          val startTime : String,
                          val startDate : String,
                          val city : String,
                          val offlineScore : Long,
                          val onlineScore : Long,
                          var isRegistered : Boolean,
                          val programs : List<Program>)