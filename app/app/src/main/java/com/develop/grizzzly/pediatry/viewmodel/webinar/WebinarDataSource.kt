package com.develop.grizzzly.pediatry.viewmodel.webinar

import androidx.paging.PositionalDataSource
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.Webinar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WebinarDataSource : PositionalDataSource<Webinar>() {

    private val apiService = WebAccess.pediatryApi
    private val database = DatabaseAccess.database

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Webinar>) {
        GlobalScope.launch {
            try {
                if (!WebAccess.offlineLog) {
                    val response = apiService.getWebinars(0, params.requestedLoadSize.toLong())
                    when {
                        response.isSuccessful -> {
                            val listing = response.body()
                            database.webinarDao().saveWebinar(listing?.response ?: listOf())
                            callback.onResult(listing?.response ?: listOf(), 0)
                        }
                    }
                } else {
                    val user = DatabaseAccess.database.userDao().findUser(0)
                    val response = WebAccess.pediatryApi.login(user?.email, user?.password)
                    if (response.isSuccessful) {
                        WebAccess.id = response.body()?.response?.id ?: 0
                        WebAccess.token = response.body()?.response?.token ?: ""
                        val responseNews =
                            apiService.getWebinars(0, params.requestedLoadSize.toLong())
                        when {
                            responseNews.isSuccessful -> {
                                val listing = responseNews.body()
                                database.webinarDao().saveWebinar(listing?.response ?: listOf())
                                callback.onResult(listing?.response ?: listOf(), 0)
                            }
                        }

                    } else {
                        val news =
                            database.webinarDao().getWebinar(0, params.requestedLoadSize.toLong())
                        callback.onResult(news, 0)
                    }
                }

            } catch (e: Exception) {
                val news = database.webinarDao().getWebinar(0, params.requestedLoadSize.toLong())
                callback.onResult(news, 0)
            }
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Webinar>) {
        GlobalScope.launch {
            try {
                val response =
                    apiService.getWebinars(params.startPosition.toLong(), params.loadSize.toLong())
                when {
                    response.isSuccessful -> {
                        val listing = response.body()
                        database.webinarDao().saveWebinar(listing?.response ?: listOf())
                        callback.onResult(listing?.response ?: listOf())
                    }
                }
            } catch (e: Exception) {
                val list = database.webinarDao()
                    .getWebinar(params.startPosition.toLong(), params.loadSize.toLong())
                callback.onResult(list)
            }
        }
    }

}