package com.develop.grizzzly.pediatry.viewmodel.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.develop.grizzzly.pediatry.network.model.News

class NewsViewModel : ViewModel() {

    companion object {
        const val pageSize = 10
    }

    var newsLiveData : LiveData<PagedList<News>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        newsLiveData = initializedPagedListBuilder(config)
    }

    private fun initializedPagedListBuilder(config: PagedList.Config): LiveData<PagedList<News>> {

        val dataSourceFactory = object : DataSource.Factory<Int, News>() {
            override fun create(): DataSource<Int, News> {
                return NewsDataSource()
            }
        }

        return LivePagedListBuilder<Int, News>(dataSourceFactory, config).build()
    }

    fun getNews() : LiveData<PagedList<News>> = newsLiveData
}