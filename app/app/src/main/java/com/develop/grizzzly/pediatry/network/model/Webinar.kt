package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Webinar constructor(@PrimaryKey val id : Long,
                               val title : String,
                               val subTitle : String?,
                               val directionId : Long?,
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


//{"id":30,"description":null,"announceImage":null,"image":null,"youtubeCode1":null,"videoTitle1":null,"videoDescription1":null,"youtubeCode2":null,"videoTitle2":null,"videoDescription2":null,"youtubeCode3":null,"videoTitle3":null,"videoDescription3":null}