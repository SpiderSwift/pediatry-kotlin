package com.develop.grizzzly.pediatry.viewmodel.speciality

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.develop.grizzzly.pediatry.network.model.Speciality


class SpecialitiesDataSourceFactory constructor(private val type: Int) :
    DataSource.Factory<Int, Speciality>() {
    var postLiveData: MutableLiveData<SpecialitiesDataSource>? = null

    override fun create(): DataSource<Int, Speciality> {
        val dataSource = SpecialitiesDataSource(type)
        postLiveData = MutableLiveData()
        postLiveData?.postValue(dataSource)
        return dataSource
    }
}