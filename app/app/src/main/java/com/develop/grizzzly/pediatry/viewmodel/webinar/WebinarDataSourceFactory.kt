package com.develop.grizzzly.pediatry.viewmodel.webinar

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.develop.grizzzly.pediatry.network.model.Webinar

class WebinarDataSourceFactory : DataSource.Factory<Int, Webinar>() {

    var postLiveData: MutableLiveData<WebinarDataSource>? = null

    override fun create(): DataSource<Int, Webinar> {
        val dataSource = WebinarDataSource()
        postLiveData = MutableLiveData()
        postLiveData?.postValue(dataSource)
        return dataSource
    }

}