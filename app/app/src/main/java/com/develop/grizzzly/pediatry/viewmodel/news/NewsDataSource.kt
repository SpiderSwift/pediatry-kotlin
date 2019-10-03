package com.develop.grizzzly.pediatry.viewmodel.news

import android.util.Log
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
                    val response = apiService.getNews(0, 10)
                    when {
                        response.isSuccessful -> {
                            val listing = response.body()
                            database.newsDao().saveNews(listing?.response ?: listOf())

                            val ads = database.adDao().loadAds()
                            Log.d("TAG", ads.toString())
                            val mutableList = listing?.response?.toMutableList()!!
                            if (ads.isNotEmpty()) {
                                val newsFromAd = ads[0].toNews()
                                mutableList.add(newsFromAd)
                            }
                            callback.onResult(mutableList,0)
                        }
                    }
                } else {
                    val user = DatabaseAccess.database.userDao().findUser(0)
                    val response = WebAccess.pediatryApi.login(user?.email, user?.password)
                    if (response.isSuccessful) {
                        WebAccess.id = response.body()?.response?.id ?: 0
                        WebAccess.token = response.body()?.response?.token ?: ""
                        val responseNews = apiService.getNews(0, 10)
                        when {
                            responseNews.isSuccessful -> {
                                val listing = responseNews.body()

                                val ads = database.adDao().loadAds()


                                val mutableList = listing?.response?.toMutableList()!!
                                if (ads.isNotEmpty()) {
                                    val newsFromAd = ads[0].toNews()
                                    mutableList.add(newsFromAd)
                                }



                                database.newsDao().saveNews(listing?.response ?: listOf())
                                callback.onResult(mutableList,0)
                            }
                        }


                    } else {
                        val news = database.newsDao().getNews(0, 10)

                        val ads = database.adDao().loadAds()
                        val mutableList = news.toMutableList()
                        if (ads.isNotEmpty()) {
                            val newsFromAd = ads[0].toNews()
                            mutableList.add(newsFromAd)
                        }
                        callback.onResult(mutableList,0)
                    }
                }

            } catch (e : Exception) {
                val news = database.newsDao().getNews(0, 10)
                callback.onResult(news,0)
            }

        }

    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<News>) {
        var index = params.startPosition.toLong() / 10
        while (index >= 10) {
            index /= 10
        }
        GlobalScope.launch {
            try {
                val response = apiService.getNews(params.startPosition.toLong(), params.loadSize.toLong())
                when {
                    response.isSuccessful -> {

                        val listing = response.body()

                        val ads = database.adDao().loadAds()
                        val mutableList = listing?.response?.toMutableList()
                        if (ads.isNotEmpty()) {
                            val newsFromAd = ads[0].toNews()
                            mutableList?.add(newsFromAd)
                        }

                        database.newsDao().saveNews(listing?.response ?: listOf())
                        callback.onResult(mutableList ?: listOf())
                    }
                    else -> {
                        val news = database.newsDao().getNews(params.startPosition.toLong(), params.loadSize.toLong())
                        val ads = database.adDao().loadAds()
                        val mutableList = news.toMutableList()
                        if (ads.isNotEmpty()) {
                            val newsFromAd = ads[0].toNews()
                            mutableList.add(newsFromAd)
                        }
                        callback.onResult(mutableList)
                    }
                }
            } catch (e : Exception) {
                val news = database.newsDao().getNews(params.startPosition.toLong(), params.loadSize.toLong())

                val ads = database.adDao().loadAds()
                val mutableList = news.toMutableList()
                if (ads.isNotEmpty()) {
                    val newsFromAd = ads[0].toNews()
                    mutableList.add(newsFromAd)
                }
                callback.onResult(mutableList)
            }

        }
    }




}