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

    suspend fun load(offset: Long, limit: Long) : MutableList<News> {
        if (WebAccess.offlineLog)
            WebAccess.tryLogin()
        val ads = database.adDao().loadAds().map { it.toNews() }.toMutableList()
        ads.forEach { Log.w("ADS", it.toString()) }
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
        var i = 0
        ads.forEach {
            i += 4
            try {
                news.add(i, it)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return news
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<News>) {
        Log.w("ADS", "loadInitial")
        GlobalScope.launch {
            callback.onResult(load(0, 10), 0)
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<News>) {
        Log.w("ADS", "loadRange")
        GlobalScope.launch {
            callback.onResult(load(params.startPosition.toLong(), params.loadSize.toLong()))
        }
    }

}