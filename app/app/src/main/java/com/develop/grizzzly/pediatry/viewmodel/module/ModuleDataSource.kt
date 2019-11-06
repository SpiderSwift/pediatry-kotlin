package com.develop.grizzzly.pediatry.viewmodel.module

import androidx.paging.PositionalDataSource
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.Module
import com.develop.grizzzly.pediatry.network.model.Webinar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ModuleDataSource : PositionalDataSource<Module>() {

    private val apiService = WebAccess.pediatryApi
    private val database = DatabaseAccess.database

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Module>) {
        GlobalScope.launch {
            if (!WebAccess.isLoggedIn)
                WebAccess.tryLoginWithDb()
            try {
                val modulesResult = apiService.getModules(0, params.requestedLoadSize.toLong())
                if (modulesResult.isSuccessful) {
                    val modules = modulesResult.body()?.response.orEmpty()
                    database.moduleDao().saveModules(modules)
                    callback.onResult(modules, 0)
                } else {
                    throw Exception("fail: loadInitial webinars from server, fallback to offline")
                }
            } catch (e: Exception) {
                val modules = database.moduleDao().getModules()
                callback.onResult(modules, 0)
            }
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Module>) {
        GlobalScope.launch {
            try {
                val webinarsResult = apiService.getModules(params.startPosition.toLong(), params.loadSize.toLong())
                if (webinarsResult.isSuccessful) {
                    val webinars = webinarsResult.body()?.response.orEmpty()
                    database.moduleDao().saveModules(webinars)
                    callback.onResult(webinars)
                } else {
                    throw Exception("fail: loadRange webinars from server, fallback to offline")
                }
            } catch (e: Exception) {
                val modules = database.moduleDao().getModules()
                callback.onResult(modules)
            }
        }
    }

}