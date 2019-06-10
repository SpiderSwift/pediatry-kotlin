package com.develop.grizzzly.pediatry.viewmodel.news

import androidx.paging.PageKeyedDataSource
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.News
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsDataSource : PageKeyedDataSource<Long, News>() {

    private val apiService = WebAccess.pediatryApi

    companion object {
        const val pageSize = 10L
    }

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, News>) {
        GlobalScope.launch {
            val response = apiService.getNews(0 , pageSize)
            when{
                response.isSuccessful -> {
                    val listing = response.body()
                    callback.onResult(listing ?: listOf(), null, 2)
                }
            }

        }

    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, News>) {
        GlobalScope.launch {
            val response = apiService.getNews((params.key - 1) * pageSize , pageSize)
            when{
                response.isSuccessful -> {
                    val listing = response.body()
                    callback.onResult(listing ?: listOf(), params.key + 1)
                }
            }
        }

    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, News>) {
        GlobalScope.launch {
            val response = apiService.getNews((params.key - 1) * pageSize , pageSize)
            when{
                response.isSuccessful -> {
                    val listing = response.body()
                    callback.onResult(listing ?: listOf(), params.key - 1)
                }
            }
        }

    }


}