package com.develop.grizzzly.pediatry.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.develop.grizzzly.pediatry.network.model.Webinar

@Dao
interface WebinarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWebinar(c: List<Webinar>)

    @Query("SELECT * FROM webinar ORDER BY startDate DESC LIMIT :limit OFFSET :offset")
    suspend fun getWebinar(offset: Long, limit: Long): List<Webinar>
}