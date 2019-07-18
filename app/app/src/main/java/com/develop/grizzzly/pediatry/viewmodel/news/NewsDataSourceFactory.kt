package com.develop.grizzzly.pediatry.viewmodel.news

import androidx.paging.DataSource
import com.develop.grizzzly.pediatry.network.model.News
import androidx.lifecycle.MutableLiveData



class NewsDataSourceFactory : DataSource.Factory<Int, News>() {

    var postLiveData: MutableLiveData<NewsDataSource>? = null

    override fun create(): DataSource<Int, News> {
        val dataSource = NewsDataSource()
        postLiveData = MutableLiveData()
        postLiveData?.postValue(dataSource)
        return dataSource
    }

}