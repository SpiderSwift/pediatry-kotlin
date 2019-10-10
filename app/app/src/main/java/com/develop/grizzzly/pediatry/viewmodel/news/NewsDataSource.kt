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

    suspend fun load(offset: Long, limit: Long) : MutableList<News> {
        if (WebAccess.offlineLog)
            WebAccess.tryLogin()
        val ads = database.adDao().loadAds().map { it.toNews() }.toMutableList()
        var news: MutableList<News>
        try {
            val responseNews = apiService.getNews(offset, limit)
            if (responseNews.isSuccessful) {
                news = responseNews.body()?.response?.toMutableList()!!
                database.newsDao().saveNews(news)
            } else {
                news = mutableListOf()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            news = database.newsDao().getNews(offset, limit).toMutableList()
        }
        ads.addAll(news)
        return ads
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<News>) {
        GlobalScope.launch {
            callback.onResult(load(0, 10), 0)
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<News>) {
        GlobalScope.launch {
            callback.onResult(load(params.startPosition.toLong(), params.loadSize.toLong()))
        }
    }

}