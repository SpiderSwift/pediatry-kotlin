package com.develop.grizzzly.pediatry.network

import com.develop.grizzzly.pediatry.BuildConfig
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.network.model.Answer
import com.develop.grizzzly.pediatry.network.model.Question
import com.develop.grizzzly.pediatry.network.model.UserToken
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

object WebAccess {


    // TODO: use https://dev.edu-pediatrics.com/ for debug?
    private val pediatryApiUrl = if (BuildConfig.DEBUG) {
        "https://edu-pediatrics.com/api/v1/"
    } else {
        "https://edu-pediatrics.com/api/v1/"
    }

    const val adsApiEndpoint = "/api/"

    var adsUrl: String? = null
    var adsApiUrl: String? = null

    var isLoggedIn: Boolean = false
        private set
        get() = token != null

    private val defaultToken: UserToken = UserToken("", 0L)
    private var token: UserToken? = null

    fun token() : UserToken = token ?: defaultToken

    fun token(token: UserToken?) {
        if (token != null)
            this.token = token
    }

    suspend fun tryLoginWithDb() {
        try {
            val user = DatabaseAccess.database.userDao().findUser(0)
            tryLogin(user?.email, user?.password)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun tryLogin(email: String?, password: String?) {
        val loginResult = pediatryApi.login(email, password)
        if (loginResult.isSuccessful)
            token = loginResult.body()?.response
    }

    val pediatryApi: PediatryApiClient by lazy {

        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${token?.token}")
                    .build()
                return@addInterceptor it.proceed(request)
            }
            .addInterceptor(loggingInterceptor)
            .build()

        val moshi = Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(pediatryApiUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return@lazy retrofit.create(PediatryApiClient::class.java)
    }

    val adsApi: AdApiClient by lazy {

        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val moshi = Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(adsApiUrl ?: "127.0.0.1")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return@lazy retrofit.create(AdApiClient::class.java)
    }
}