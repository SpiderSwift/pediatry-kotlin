package com.develop.grizzzly.pediatry.viewmodel.news

import android.util.Log
import androidx.annotation.IntRange
import androidx.paging.PositionalDataSource
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.News
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class NewsDataSource : PositionalDataSource<News>() {

    private val apiService = WebAccess.pediatryApi
    private val database = DatabaseAccess.database

    private fun <T> mixin(
        src: List<T>,
        dst: MutableList<T>,
        @Suppress("SameParameterValue")
        @IntRange(from = 1)
        step: Int
    ): MutableList<T> {
        for ((i) in src.withIndex()) {
            try {
                dst.add(step * (i + 1), src[i])
            } catch (e: Exception) {
                e.printStackTrace()
                dst.add(src[i])
                break
            }
        }
        return dst
    }

    suspend fun load(offset: Long, limit: Long): MutableList<News> {
        if (!WebAccess.isLoggedIn)
            WebAccess.tryLoginWithDb()
        val ads = database.adDao().loadAds().map { it.convert() }
        var news: MutableList<News>
        try {
            val responseNews = apiService.getNews(offset, limit)
            if (responseNews.isSuccessful) {
                news = responseNews.body()?.response?.toMutableList()!!
                val list = ArrayList<Long>()
                news.add(
                    News(
                        666,
                        "test ad",
                        "Какое-то описание видоса",
                        Date(),
                        "",
                        3,
                        list,
                        true,
                        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                    )
                )
                news.reverse()
                database.newsDao().saveNews(news)
            } else {
                news = mutableListOf()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            news = database.newsDao().getNews(offset, limit).toMutableList()
        }
        mixin(ads, news, 4)
        return news
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<News>) {
        GlobalScope.launch {
            // TODO: here must be logic for numbers calc?
            callback.onResult(load(0, 10), 0)
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<News>) {
        GlobalScope.launch {
            callback.onResult(load(params.startPosition.toLong(), params.loadSize.toLong()))
        }
    }

}