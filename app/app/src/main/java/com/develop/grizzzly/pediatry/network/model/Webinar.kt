package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Webinar constructor(
    @PrimaryKey val id: Long,
    val title: String,
    val subTitle: String?,
    val directionId: Long?,
    val description: String?,
    val startDate: Date?,
    val startTime: String,
    val endTime: String,
    val score: Long,
    val confirmationTime1: String?,
    val confirmationTime2: String?,
    val confirmationTime3: String?,
    val youtubeCode: String?,
    val email: String?,
    var isRegistered: Boolean = false,
    var status: Long = 1
)

//{"id":30,"description":null,"announceImage":null,"image":null,"youtubeCode1":null,"videoTitle1":null,"videoDescription1":null,"youtubeCode2":null,"videoTitle2":null,"videoDescription2":null,"youtubeCode3":null,"videoTitle3":null,"videoDescription3":null}

//"id": 35, // идентификатор вебинара
//            "title": "Цикл видеодиалогов «Современная педiятрика»", // название вебинара
//            "subTitle": "Тема вебинара", // подзаголовок вебинара
//            "directionId": 55, // направление вебинара (необязательный)
//            "description": "Информация о вебинаре", // описание вебинара
//            "startDate": "2019-09-15", // дата проведения вебинара
//            "startTime": "11:00", // время начала вебинара
//            "endTime": "12:00", // время окончания вебинара
//            "score": 0, // баллы, начисляемые за просмотр вебинара
//            "confirmationTime1": "2019-09-15 11:20", // время показа 1-го окна подтверждения просмотра вебинара
//            "confirmationTime2": "2019-09-15 11:40", // время показа 2-го окна подтверждения просмотра вебинара
//            "confirmationTime3": "2019-09-15 11:40", // время показа 3-го окна подтверждения просмотра вебинара
//            "youtubeCode": "<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/dQxN_r2OCE8\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>", // код видео вебинара
//            "email": "support@edu-pediatrics.com"
//            "isRegistered": false, // признак, показывающий зарегистрирован ли пользователь на данную конференцию
//        "status": 1 // статус вебинара, смотрите ниже