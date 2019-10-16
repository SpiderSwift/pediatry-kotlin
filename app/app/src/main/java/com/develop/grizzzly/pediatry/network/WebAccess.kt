package com.develop.grizzzly.pediatry.network

import com.develop.grizzzly.pediatry.BuildConfig
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.db.model.User
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

    val adsApiEndpoint = "/api/"
    var adsUrl: String = ""
    var adsApiUrl: String = ""

    var userToken: String = ""
    var userId: Long = 0
    var offlineLog: Boolean = true

    suspend fun tryLoginWithDb() {
        try {
            val user = DatabaseAccess.database.userDao().findUser(0)
            tryLogin(user?.email, user?.password)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun tryLogin(email: String?, password: String?) {
        val loginResult = pediatryApi.login(email, password)
        if (loginResult.isSuccessful) {
            userId = loginResult.body()?.response?.id ?: 0
            userToken = loginResult.body()?.response?.token ?: ""
        }
    }

    val pediatryApi: PediatryApiClient by lazy {

        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request().newBuilder()
                    .addHeader("Authorization", "Bearer $userToken")
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
            .baseUrl(adsApiUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return@lazy retrofit.create(AdApiClient::class.java)
    }
}