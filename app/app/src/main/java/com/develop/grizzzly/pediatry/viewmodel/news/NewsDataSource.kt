package com.develop.grizzzly.pediatry.viewmodel.news

import androidx.paging.PositionalDataSource
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.News
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsDataSource : PositionalDataSource<News>() {

    private val apiService = WebAccess.pediatryApi
    private val database = DatabaseAccess.database

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<News>) {
        GlobalScope.launch {
            try {
                if (!WebAccess.offlineLog) {
                    val response = apiService.getNews(0, params.requestedLoadSize.toLong())
                    when {
                        response.isSuccessful -> {
                            val listing = response.body()
                            database.newsDao().saveNews(listing?.response ?: listOf())
                            callback.onResult(listing?.response ?: listOf(),0)
                        }
                    }
                } else {
                    val user = DatabaseAccess.database.userDao().findUser(0)
                    val response = WebAccess.pediatryApi.login(user?.email, user?.password)
                    if (response.isSuccessful) {
                        WebAccess.id = response.body()?.response?.id ?: 0
                        WebAccess.token = response.body()?.response?.token ?: ""
                        val responseNews = apiService.getNews(0, params.requestedLoadSize.toLong())
                        when {
                            responseNews.isSuccessful -> {
                                val listing = responseNews.body()
                                database.newsDao().saveNews(listing?.response ?: listOf())
                                callback.onResult(listing?.response ?: listOf(),0)
                            }
                        }


                    } else {
                        val news = database.newsDao().getNews(0, params.requestedLoadSize.toLong())
                        callback.onResult(news,0)
                    }
                }

            } catch (e : Exception) {
                val news = database.newsDao().getNews(0, params.requestedLoadSize.toLong())
                callback.onResult(news,0)
            }

        }

    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<News>) {
        GlobalScope.launch {
            try {
                val response = apiService.getNews(params.startPosition.toLong(), params.loadSize.toLong())
                when {
                    response.isSuccessful -> {
                        val listing = response.body()
                        database.newsDao().saveNews(listing?.response ?: listOf())
                        callback.onResult(listing?.response ?: listOf())
                    }
                }
            } catch (e : Exception) {
                val news = database.newsDao().getNews(params.startPosition.toLong(), params.loadSize.toLong())
                callback.onResult(news)
            }

        }
    }




}