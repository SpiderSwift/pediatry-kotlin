package com.develop.grizzzly.pediatry.viewmodel.conference

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.develop.grizzzly.pediatry.network.model.Conference

class ConferenceDataSourceFactory : DataSource.Factory<Int, Conference>() {

    var postLiveData: MutableLiveData<ConferenceDataSource>? = null

    override fun create(): DataSource<Int, Conference> {
        val dataSource = ConferenceDataSource()
        postLiveData = MutableLiveData()
        postLiveData?.postValue(dataSource)
        return dataSource
    }

}