package com.develop.grizzzly.pediatry.viewmodel.conference

import android.util.Log
import androidx.paging.PositionalDataSource
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.Conference
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ConferenceDataSource : PositionalDataSource<Conference>() {

    private val apiService = WebAccess.pediatryApi

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Conference>) {
        GlobalScope.launch {

            val resp = WebAccess.pediatryApi.getConference(115)
            if (resp.isSuccessful) {
                Log.d("TAG", resp.body().toString())
            } else {
                Log.d("TAG", resp.errorBody()?.string())
            }

            val response = apiService.getConferences(0, params.requestedLoadSize.toLong())
            when {
                response.isSuccessful -> {
                    val listing = response.body()
                    Log.d("TAG", listing.toString())
                    callback.onResult(listing?.response ?: listOf(),0)
                }
            }
        }

    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Conference>) {
        GlobalScope.launch {
            val response = apiService.getConferences(params.startPosition.toLong(), params.loadSize.toLong())
            when {
                response.isSuccessful -> {
                    val listing = response.body()
                    callback.onResult(listing?.response ?: listOf())
                }
            }
        }
    }

}