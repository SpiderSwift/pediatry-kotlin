package com.develop.grizzzly.pediatry.viewmodel.module

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.develop.grizzzly.pediatry.network.model.Module

class ModuleViewModel : ViewModel() {

    companion object {
        const val pageSize = 10
    }

    var conferenceLiveData: LiveData<PagedList<Module>>
    var dataSourceFactory: ModuleDataSourceFactory = ModuleDataSourceFactory()

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        conferenceLiveData = LivePagedListBuilder<Int, Module>(dataSourceFactory, config).build()
    }
}