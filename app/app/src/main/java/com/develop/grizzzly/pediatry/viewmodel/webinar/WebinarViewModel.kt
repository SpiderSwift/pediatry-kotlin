package com.develop.grizzzly.pediatry.viewmodel.webinar

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.develop.grizzzly.pediatry.network.model.Webinar

class WebinarViewModel : ViewModel() {


    companion object {
        const val pageSize = 10
    }

    var conferenceLiveData: LiveData<PagedList<Webinar>>
    var dataSourceFactory: WebinarDataSourceFactory = WebinarDataSourceFactory()

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        conferenceLiveData = LivePagedListBuilder<Int, Webinar>(dataSourceFactory, config).build()
    }
}