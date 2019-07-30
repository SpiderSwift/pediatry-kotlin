package com.develop.grizzzly.pediatry.viewmodel.news

import android.util.Log
import androidx.paging.PositionalDataSource
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.News
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsDataSource : PositionalDataSource<News>() {

    private val apiService = WebAccess.pediatryApi

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<News>) {
        GlobalScope.launch {
            val response = apiService.getNews(0, params.requestedLoadSize.toLong())
            when {
                response.isSuccessful -> {
                    val listing = response.body()
                    Log.d("TAG", listing?.response.toString())
                    callback.onResult(listing?.response ?: listOf(),0)
                }
            }
        }

    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<News>) {
        GlobalScope.launch {
            val response = apiService.getNews(params.startPosition.toLong(), params.loadSize.toLong())
            when {
                response.isSuccessful -> {
                    val listing = response.body()
                    callback.onResult(listing?.response ?: listOf())
                }
            }
        }
    }

}