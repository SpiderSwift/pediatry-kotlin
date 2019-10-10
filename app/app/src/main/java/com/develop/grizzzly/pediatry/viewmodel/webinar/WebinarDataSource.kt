package com.develop.grizzzly.pediatry.viewmodel.webinar

import androidx.paging.PositionalDataSource
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.Webinar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WebinarDataSource : PositionalDataSource<Webinar>() {

    private val apiService = WebAccess.pediatryApi
    private val database = DatabaseAccess.database

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Webinar>) {
        GlobalScope.launch {
            if (WebAccess.offlineLog)
                WebAccess.tryLogin()
            try {
                val webinarsResult = apiService.getWebinars(0, params.requestedLoadSize.toLong())
                if (webinarsResult.isSuccessful) {
                    val webinars = webinarsResult.body()?.response.orEmpty()
//                    webinars.forEach({it.startDate = it})
                    database.webinarDao().saveWebinar(webinars)
                    callback.onResult(webinars, 0)
                } else {
                    throw Exception("failed to get webinars list from server, fallback to offline")
                }
            } catch (e: Exception) {
                val webinars = database.webinarDao().getWebinar(0, params.requestedLoadSize.toLong())
                callback.onResult(webinars, 0)
            }
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Webinar>) {
        GlobalScope.launch {
            try {
                val response =
                    apiService.getWebinars(params.startPosition.toLong(), params.loadSize.toLong())
                when {
                    response.isSuccessful -> {
                        val listing = response.body()
                        database.webinarDao().saveWebinar(listing?.response ?: listOf())
                        callback.onResult(listing?.response ?: listOf())
                    }
                }
            } catch (e: Exception) {
                val list = database.webinarDao()
                    .getWebinar(params.startPosition.toLong(), params.loadSize.toLong())
                callback.onResult(list)
            }
        }
    }

}