package com.develop.grizzzly.pediatry.network.model

data class ModulePost (
    val id: Int,
    val title: String,
    val directionId: Int,
    val number: Int,
    val youtubeCode: String?,
    val clinicalAnalysisDescription: String,
    val testStatus: Int,
    val slides: MutableList<Slide>,
    val articles: MutableList<Book>
)