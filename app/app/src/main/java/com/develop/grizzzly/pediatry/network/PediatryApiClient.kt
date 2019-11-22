package com.develop.grizzzly.pediatry.network

import com.develop.grizzzly.pediatry.network.model.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface PediatryApiClient {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("login") login: String?,
        @Field("password") password: String?
    ): Response<BasicResponse<UserToken>>

    @Multipart
    //@FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Part("name") name: RequestBody,
        @Part("lastname") lastname: RequestBody,
        @Part("middlename") middlename: RequestBody?,
        @Part("email") email: RequestBody,
        @Part("city") city: RequestBody,
        @Part("full_city") fullCity: RequestBody,
        @Part("country") country: RequestBody,
        @Part("kladr_id") kladrId: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("main_specialty_id") mainId: RequestBody,
        @Part("additional_specialty_1_id") additionalId1: RequestBody?,
        @Part("additional_specialty_2_id") additionalId2: RequestBody?,
        //@Part avatar : MultipartBody.Part
        @Part("avatar\"; filename=\"file.jpg\" ") avatar: RequestBody?,
        @Part("password") password: RequestBody
    ): Response<ResponseBody>

    @GET("user/profile")
    suspend fun getProfile(): Response<BasicResponse<ProfileResponse>>

    @Multipart
//    @FormUrlEncoded
    @POST("user/profile")
    suspend fun updateProfile(
        @Part("name") name: RequestBody,
        @Part("lastname") lastname: RequestBody,
        @Part("middlename") middlename: RequestBody,
        @Part("email") email: RequestBody?,
        @Part("city") city: RequestBody,
        @Part("full_city") fullCity: RequestBody,
        @Part("country") country: RequestBody,
        @Part("kladr_id") kladrId: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("main_specialty_id") mainId: RequestBody?,
        @Part("additional_specialty_1_id") additionalId1: RequestBody?,
        @Part("additional_specialty_2_id") additionalId2: RequestBody?,
        @Part("avatar\"; filename=\"file.jpg\" ") avatar: RequestBody?
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("user/password/restore")
    suspend fun restorePassword(
        @Field("email") email: String
    ): Response<ResponseBody>

    @GET("news")
    suspend fun getNews(
        @Query("offset") offset: Long,
        @Query("limit") limit: Long
    ): Response<BasicResponse<List<News>>>

    @GET("news/{id}")
    suspend fun getNewsById(
        @Path("id") newsId: Long
    ): Response<BasicResponse<NewsPost>>

    @POST("news/{id}/like")
    suspend fun likeNews(
        @Path("id") newsId: Long?
    ): Response<ResponseBody>

    @DELETE("news/{id}/like")
    suspend fun unlikeNews(
        @Path("id") newsId: Long?
    ): Response<ResponseBody>

    @GET("ads-url")
    suspend fun getAdsUrl(): Response<BasicResponse<AdUrl>>

    @GET("specialty/main")
    suspend fun getMainSpecs(): Response<BasicResponse<List<Speciality>>>

    @GET("specialty/additional")
    suspend fun getExtraSpecs(): Response<BasicResponse<List<Speciality>>>

    @GET("conference")
    suspend fun getConferences(
        @Query("offset") offset: Long,
        @Query("limit") limit: Long
    ): Response<BasicResponse<List<Conference>>>

    @GET("conference/{id}")
    suspend fun getConference(
        @Path("id") conferenceId: Long
    ): Response<BasicResponse<ConferenceItem>>

    @POST("conference/{id}/register/offline")
    suspend fun registerForConference(
        @Path("id") conferenceId: Long
    ): Response<ResponseBody>

    @DELETE("conference/{id}/register/offline")
    suspend fun unregisterForConference(
        @Path("id") conferenceId: Long
    ): Response<ResponseBody>

    @GET("webinar")
    suspend fun getWebinars(
        @Query("offset") offset: Long,
        @Query("limit") limit: Long
    ): Response<BasicResponse<List<Webinar>>>

    @GET("webinar/{id}")
    suspend fun getWebinar(
        @Path("id") webinarId: Long
    ): Response<BasicResponse<Webinar>>

    @POST("webinar/{id}/register")
    suspend fun registerForWebinar(
        @Path("id") webinarId: Long
    ): Response<ResponseBody>

    @DELETE("webinar/{id}/register")
    suspend fun unregisterForWebinar(
        @Path("id") webinarId: Long
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("webinar/{id}/message")
    suspend fun sendMessageForWebinar(
        @Path("id") webinarId: Long,
        @Field("message") message: String
    ): Response<ResponseBody>

    @GET("question")
    suspend fun getQuestions(
        @Query("fromTs") fromTs: String?
    ): Response<BasicResponse<List<QuestionApi>>>

    @GET("module")
    suspend fun getModules(
        @Query("offset") offset: Long,
        @Query("limit") limit: Long
    ): Response<BasicResponse<List<Module>>>

    @GET("module/{id}")
    suspend fun getModuleById(
        @Path("id") moduleId: Long
    ): Response<BasicResponse<ModulePost>>

    @GET("module/{id}/question")
    suspend fun getModulesQuestion(
        @Path("id") id: String
    ): Response<BasicResponse<List<String>>>
}