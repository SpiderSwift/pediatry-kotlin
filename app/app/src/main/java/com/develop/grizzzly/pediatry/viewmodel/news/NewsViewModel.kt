package com.develop.grizzzly.pediatry.viewmodel.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.develop.grizzzly.pediatry.network.model.News

class NewsViewModel : ViewModel() {
    var newsLiveData  : LiveData<PagedList<News>>

    init {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .build()
        newsLiveData  = initializedPagedListBuilder(config)
    }



    private fun initializedPagedListBuilder(config: PagedList.Config):
            LiveData<PagedList<News>> {

        val dataSourceFactory = object : DataSource.Factory<Long, News>() {
            override fun create(): DataSource<Long, News> {
                return NewsDataSource()
            }
        }

        return LivePagedListBuilder<Long,News>(dataSourceFactory, config).build()
    }

    fun getNews():LiveData<PagedList<News>> = newsLiveData
}