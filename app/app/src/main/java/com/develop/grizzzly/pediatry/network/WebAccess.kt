package com.develop.grizzzly.pediatry.network

import com.develop.grizzzly.pediatry.application.ThisApp
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.network.model.UserToken
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

object WebAccess {

    // TODO: use https://dev.edu-pediatrics.com/ for debug?
    private val pediatryApiUrl = if (ThisApp.dev) {
        "https://dev.edu-pediatrics.com/api/v1/"
    } else {
        "https://edu-pediatrics.com/api/v1/"
    }

    const val adsApiEndpoint = "/api/"

    var adsUrl: String? = null
    var adsApiUrl: String? = null

    private var userToken: UserToken? = null

    // TODO: review logic with disabling work without log-in
    var isLoggedIn: Boolean = false
        private set
        get() = userToken != null

    // TODO: review logic with disabling work without log-in
    fun token() : UserToken = userToken ?: defaultUserToken

    // TODO: review logic with disabling work without log-in
    private val defaultUserToken: UserToken = UserToken("", 0L)

    // TODO: review logic with disabling work without log-in
    fun token(token: UserToken?) {
        if (token != null)
            this.userToken = token
    }

    // TODO: review logic with disabling work without log-in
    suspend fun tryLoginWithDb() {
        try {
            val user = DatabaseAccess.database.userDao().findUser(0)
            tryLogin(user?.email, user?.password)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // TODO: review logic with disabling work without log-in
    private suspend fun tryLogin(email: String?, password: String?) {
        val loginResult = pediatryApi.login(email, password)
        if (loginResult.isSuccessful)
            userToken = loginResult.body()?.response
    }

    val moshi : Moshi by lazy {
        return@lazy Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()
    }

    val pediatryApi: PediatryApiClient by lazy {

        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor {
                val requestBuilder = it.request().newBuilder()
                userToken?.token?.let { tokenString ->
                    requestBuilder.addHeader("Authorization", "Bearer $tokenString")
                }
                return@addInterceptor it.proceed(requestBuilder.build())
            }
            .addInterceptor(loggingInterceptor)
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

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(adsApiUrl ?: "127.0.0.1")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return@lazy retrofit.create(AdApiClient::class.java)
    }
}