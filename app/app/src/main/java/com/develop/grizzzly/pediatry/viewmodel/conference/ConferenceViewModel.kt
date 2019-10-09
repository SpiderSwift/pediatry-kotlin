package com.develop.grizzzly.pediatry.viewmodel.conference


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.develop.grizzzly.pediatry.network.model.Conference

class ConferenceViewModel : ViewModel() {


    companion object {
        const val pageSize = 10
    }

    var conferenceLiveData: LiveData<PagedList<Conference>>
    var dataSourceFactory: ConferenceDataSourceFactory = ConferenceDataSourceFactory()

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        conferenceLiveData =
            LivePagedListBuilder<Int, Conference>(dataSourceFactory, config).build()
    }
}