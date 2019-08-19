package com.develop.grizzzly.pediatry.viewmodel.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.develop.grizzzly.pediatry.adapters.news.NewsAdapter
import com.develop.grizzzly.pediatry.network.model.News

class NewsViewModel : ViewModel() {

    companion object {
        const val pageSize = 10
    }

    var adapter : NewsAdapter? = null
    var newsLiveData : LiveData<PagedList<News>>
    var dataSourceFactory : NewsDataSourceFactory = NewsDataSourceFactory()

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        newsLiveData = LivePagedListBuilder<Int, News>(dataSourceFactory, config).build()
    }

}