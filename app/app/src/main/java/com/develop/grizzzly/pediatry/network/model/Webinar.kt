package com.develop.grizzzly.pediatry.network.model

import java.util.*

data class Webinar constructor(val id : Long,
                               val title : String,
                               val subTitle : String?,
                               val description : String?,
                               val startDate : Date?,
                               val startTime : String,
                               val endTime : String,
                               val score : Long,
                               val confirmationTime1: String?,
                               val confirmationTime2: String?,
                               val confirmationTime3: String?,
                               val youtubeCode : String?,
                               val email : String?)


//"id": 33,
//"title": "Цикл видеодиалогов «Современная педiятрика». ",
//"subTitle": null,
//"description": "",
//"startDate": "2019-10-05",
//"startTime": "11:00",
//"endTime": "00:00",
//"score": 0,
//"confirmationTime1": null,
//"confirmationTime2": null,
//"confirmationTime3": null,
//"youtubeCode": "",
//"email": ""