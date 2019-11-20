package com.develop.grizzzly.pediatry.db.dao

import androidx.annotation.Nullable
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.develop.grizzzly.pediatry.network.model.Question

@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveQuestions(q: List<Question>)

    @Query("SELECT * FROM question")
    suspend fun getQuestionsAll(): List<Question>

    @Query("SELECT * FROM question WHERE id = :id")
    suspend fun getQuestionsFromModule(id : String): Question

    @Nullable
    @Query("SELECT MAX(tsLastChange) FROM question")
    suspend fun getMaxTsLastChange(): Long?
}