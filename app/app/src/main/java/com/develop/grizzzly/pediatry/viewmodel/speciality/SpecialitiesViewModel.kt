package com.develop.grizzzly.pediatry.viewmodel.speciality

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.develop.grizzzly.pediatry.network.model.Speciality

class SpecialitiesViewModel constructor(type : Int) : ViewModel() {


    var specialitiesLiveData: LiveData<PagedList<Speciality>>
    var dataSourceFactory: SpecialitiesDataSourceFactory = SpecialitiesDataSourceFactory(type)

    init {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .build()
        specialitiesLiveData = LivePagedListBuilder<Int, Speciality>(dataSourceFactory, config).build()
    }

}