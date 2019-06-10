package com.develop.grizzzly.pediatry.network

import com.develop.grizzzly.pediatry.network.model.News
import com.develop.grizzzly.pediatry.network.model.NewsPost
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PediatryApiClient {

    @POST("login") suspend fun login(@Query("login") login : String, @Query("password") password : String) : Any
    @GET("news") suspend fun getNews(@Query("offset") offset : Long, @Query("limit") limit : Long) : Response<List<News>>
    @GET("news/{newsId}") suspend fun getNewsById(@Path("newsId") newsId : Long) : List<NewsPost>
}