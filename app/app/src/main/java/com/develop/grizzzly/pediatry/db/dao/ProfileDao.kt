package com.develop.grizzzly.pediatry.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.develop.grizzzly.pediatry.network.model.Profile

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProfile(u : Profile) : Long

    @Query(value = "SELECT * FROM profile WHERE id = :id LIMIT 1")
    suspend fun loadProfile(id : Int) : Profile?
}