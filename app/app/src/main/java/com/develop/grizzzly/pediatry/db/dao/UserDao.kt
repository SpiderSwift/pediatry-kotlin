package com.develop.grizzzly.pediatry.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.develop.grizzzly.pediatry.db.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(u : User) : Long

    @Query(value = "SELECT * FROM user WHERE id = :id LIMIT 1")
    suspend fun findUser(id : Int) : User?
}