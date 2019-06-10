package com.develop.grizzzly.pediatry.viewmodel.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.develop.grizzzly.pediatry.network.model.News

class NewsViewModel : ViewModel() {
    var postsLiveData  : LiveData<PagedList<News>>

    init {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .build()
        postsLiveData  = initializedPagedListBuilder(config)
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

    //fun getPosts():LiveData<PagedList<PartData>> = postsLiveData
}