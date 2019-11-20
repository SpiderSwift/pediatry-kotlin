package com.develop.grizzzly.pediatry.viewmodel.module

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.develop.grizzzly.pediatry.network.model.Module

class ModuleDataSourceFactory : DataSource.Factory<Int, Module>() {

    var postLiveData: MutableLiveData<ModuleDataSource>? = null

    override fun create(): DataSource<Int, Module> {
        val dataSource = ModuleDataSource()
        postLiveData = MutableLiveData()
        postLiveData?.postValue(dataSource)
        return dataSource
    }
}