package com.develop.grizzzly.pediatry.viewmodel.conference

import androidx.paging.PositionalDataSource
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.Conference
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ConferenceDataSource : PositionalDataSource<Conference>() {

    private val apiService = WebAccess.pediatryApi
    private val database = DatabaseAccess.database

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Conference>) {
        GlobalScope.launch {
            try {
                val response = apiService.getConferences(0, params.requestedLoadSize.toLong())
                when {
                    response.isSuccessful -> {
                        val listing = response.body()
                        database.conferenceDao().saveConference(listing?.response ?: listOf())
                        callback.onResult(listing?.response ?: listOf(),0)
                    }
                }
            } catch (e : Exception) {
                val list = database.conferenceDao().getConferences(0, params.requestedLoadSize.toLong())
                callback.onResult(list,0)
            }

        }

    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Conference>) {
        GlobalScope.launch {
            try {
                val response = apiService.getConferences(params.startPosition.toLong(), params.loadSize.toLong())
                when {
                    response.isSuccessful -> {
                        val listing = response.body()
                        database.conferenceDao().saveConference(listing?.response ?: listOf())
                        callback.onResult(listing?.response ?: listOf())
                    }
                }
            } catch (e : Exception) {
                val list = database.conferenceDao().getConferences(params.startPosition.toLong(), params.loadSize.toLong())
                callback.onResult(list)
            }
        }
    }

}