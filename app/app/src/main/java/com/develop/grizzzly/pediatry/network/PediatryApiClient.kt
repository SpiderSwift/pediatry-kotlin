package com.develop.grizzzly.pediatry.network

import com.develop.grizzzly.pediatry.network.model.*
import com.fasterxml.jackson.databind.JsonNode
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface PediatryApiClient {

    @FormUrlEncoded
    @POST("login") suspend fun login(
        @Field("login") login : String?,
        @Field("password") password : String?
    ) : Response<BasicResponse<TokenObject>>


    //@Multipart
    @FormUrlEncoded
    @POST("register") suspend fun register(
        @Field("name") name : String,
        @Field("lastname") lastname : String,
        @Field("middlename") middlename : String?,
        @Field("email") email : String,
        @Field("city") city : String,
        @Field("phone") phone : String,
        @Field("main_specialty_id") mainId : String,
        @Field("additional_specialty_1_id") additionalId1 : String?,
        @Field("additional_specialty_2_id") additionalId2: String?,
        @Field("password") password : String,
        @Field("avatar") avatar : String
    ) : Response<ResponseBody>

    @GET("user/profile") suspend fun getProfile() : Response<ResponseBody>
    @FormUrlEncoded
    @POST("user/profile") suspend fun updateProfile(
        @Field("name") name : String,
        @Field("lastname") lastname : String,
        @Field("middlename") middlename : String?,
        @Field("email") email : String,
        @Field("city") city : String,
        @Field("phone") phone : String,
        @Field("main_specialty_id") mainId : String,
        @Field("additional_specialty_1_id") additionalId1 : String?,
        @Field("additional_specialty_2_id") additionalId2: String?
    ) : Response<JsonNode>
    @POST("user/password") suspend fun changePassword(
        @Field("password") password: String,
        @Field("confirm_password") confirm : String
    ) : Response<JsonNode>
    @FormUrlEncoded
    @POST("user/password/restore") suspend fun restorePassword(
        @Field("email") email: String
    ) : Response<ResponseBody>

    @GET("news") suspend fun getNews(
        @Query("offset") offset : Long,
        @Query("limit") limit : Long
    ) : Response<BasicResponse<List<News>>>
    @GET("news/{id}") suspend fun getNewsById(
        @Path("id") newsId : Long
    ) : Response<JsonNode>
    @POST("news/{id}/like") suspend fun likeNews(
        @Path("id") newsId : Long
    ) : Response<JsonNode>
    @DELETE("news/{id}/like") suspend fun unlikeNews(
        @Path("id") newsId : Long
    ) : Response<JsonNode>

    @GET("specialty/main") suspend fun getMainSpecialities() : Response<BasicResponse<List<Speciality>>>
    @GET("specialty/additional") suspend fun getAdditionalSpecialities() : Response<BasicResponse<List<Speciality>>>

    @GET("conference") suspend fun getConferences() : Response<BasicResponse<List<Conference>>>
    @GET("conference/{id}") suspend fun getConference(
        @Path("id") conferenceId : Long
    ) : Response<JsonNode>
    @POST("conference/{id}/register/offline") suspend fun registerForConference(
        @Path("id") conferenceId : Long
    ) : Response<JsonNode>
    @DELETE("conference/{id}/register/offline") suspend fun unregisterForConference(
        @Path("id") conferenceId : Long
    ) : Response<JsonNode>

    @GET("broadcast") suspend fun getBroadcasts() : Response<BasicResponse<List<Broadcast>>>
    @GET("broadcast/{id}") suspend fun getBroadcast(
        @Path("id") broadcastId : Long
    ) : Response<JsonNode>
    @POST("broadcast/{id}/register") suspend fun registerForBroadcast(
        @Path("id") broadcastId : Long
    ) : Response<JsonNode>
    @POST("broadcast/{id}/confirm") suspend fun confirmForBroadcast(
        @Path("id") broadcastId : Long
    ) : Response<JsonNode>
    @DELETE("broadcast/{id}/register") suspend fun unregisterForBroadcast(
        @Path("id") broadcastId : Long
    ) : Response<JsonNode>
    @GET("broadcast/{id}/times") suspend fun getBroadcastTime(
        @Path("id") broadcastId : Long
    ) : Response<JsonNode>
    @FormUrlEncoded
    @POST("broadcast/{id}/message") suspend fun sendMessageForBroadcast(
        @Path("id") broadcastId : Long,
        @Field("message") message : String
    ) : Response<JsonNode>

    @GET("webinar") suspend fun getWebinars() : Response<BasicResponse<List<Webinar>>>
    @GET("webinar/archive") suspend fun getArchiveWebinars() : Response<JsonNode>
    @GET("webinar/{id}") suspend fun getWebinar(
        @Path("id") webinarId : Long
    ) : Response<JsonNode>
    @POST("webinar/{id}/register") suspend fun registerForWebinar(
        @Path("id") webinarId : Long
    ) : Response<JsonNode>
    @DELETE("webinar/{id}/register") suspend fun unregisterForWebinar(
        @Path("id") webinarId : Long
    ) : Response<JsonNode>
    @GET("webinar/{id}/times") suspend fun getWebinarTime(
        @Path("id") webinarId : Long
    ) : Response<JsonNode>
    @FormUrlEncoded
    @POST("webinar/{id}/message") suspend fun sendMessageForWebinar(
        @Path("id") webinarId : Long,
        @Field("message") message : String
    ) : Response<JsonNode>

}