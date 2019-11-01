package com.develop.grizzzly.pediatry.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.develop.grizzzly.pediatry.network.model.Question

@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveQuestions(q: List<Question>)

    @Query("SELECT * FROM question WHERE tsLastChange >:tsLastChange")
    suspend fun getQuestions(tsLastChange: Long): List<Question>

    @Query("SELECT *, MAX(tsLastChange) as tsLastChange FROM question")
    suspend fun getQuestionsTime(): List<Question>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveQuestion(q: Question)
}