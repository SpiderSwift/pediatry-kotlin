package com.develop.grizzzly.pediatry.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.develop.grizzzly.pediatry.network.model.Ad

@Dao
interface AdDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAds(u: List<Ad>)

    @Query(value = "SELECT * FROM ad")
    suspend fun loadAds(): List<Ad>
}