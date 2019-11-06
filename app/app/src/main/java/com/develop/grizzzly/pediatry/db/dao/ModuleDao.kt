package com.develop.grizzzly.pediatry.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.develop.grizzzly.pediatry.network.model.Module


@Dao
interface ModuleDao { //Todo
    @Query("SELECT * FROM module")
    fun getModules():List<Module>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveModules(c: List<Module>)
}