package com.develop.grizzzly.pediatry.viewmodel.module

import androidx.paging.PositionalDataSource
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.Module
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ModuleDataSource : PositionalDataSource<Module>() {

    private val apiService = WebAccess.pediatryApi
    private val database = DatabaseAccess.database

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Module>) {
        GlobalScope.launch {
            if (!WebAccess.isLoggedIn)
                WebAccess.tryLoginWithDb()
            callback.onResult(load(0, 10), 0)
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Module>) {
        GlobalScope.launch {
            callback.onResult(load(params.startPosition.toLong(), params.loadSize.toLong()))
        }
    }

    suspend fun load(offset: Long, limit: Long): MutableList<Module> {
        return try {
            val modulesResult = apiService.getModules(offset, limit)
            if (modulesResult.isSuccessful) {
                val modules = modulesResult.body()?.response.orEmpty()
                database.moduleDao().saveModules(modules)
                modules.toMutableList()
            } else {
                throw Exception("fail: loadRange modules from server, fallback to offline")
            }
        } catch (e: Exception) {
            database.moduleDao().getModules().toMutableList()
        }
    }
}