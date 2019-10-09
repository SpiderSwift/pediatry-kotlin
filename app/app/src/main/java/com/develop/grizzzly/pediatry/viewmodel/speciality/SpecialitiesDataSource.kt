package com.develop.grizzzly.pediatry.viewmodel.speciality

import androidx.paging.PageKeyedDataSource
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.Speciality
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SpecialitiesDataSource constructor(private val type: Int) :
    PageKeyedDataSource<Int, Speciality>() {

    private val apiService = WebAccess.pediatryApi

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Speciality>
    ) {
        GlobalScope.launch {
            val response = when {
                (type == 0) -> {
                    apiService.getMainSpecialities()
                }
                else -> {
                    apiService.getAdditionalSpecialities()
                }
            }

            when {
                response.isSuccessful -> {
                    val listing = response.body()?.response
                    listing?.let { callback.onResult(it, null, null) }
                }
            }

        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Speciality>) {

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Speciality>) {

    }
}