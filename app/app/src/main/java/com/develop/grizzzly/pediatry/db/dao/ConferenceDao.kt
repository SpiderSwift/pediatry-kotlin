package com.develop.grizzzly.pediatry.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.develop.grizzzly.pediatry.network.model.Conference

@Dao
interface ConferenceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveConference(c : List<Conference>)

    @Query("SELECT * FROM conference ORDER BY startDate DESC LIMIT :limit OFFSET :offset")
    suspend fun getConferences(offset : Long, limit : Long) : List<Conference>
}