package com.develop.grizzzly.pediatry.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.develop.grizzzly.pediatry.network.model.News

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNews(n: List<News>)

    @Query("SELECT * FROM news ORDER BY date DESC LIMIT :limit OFFSET :offset")
    suspend fun getNews(offset: Long, limit: Long): List<News>

}