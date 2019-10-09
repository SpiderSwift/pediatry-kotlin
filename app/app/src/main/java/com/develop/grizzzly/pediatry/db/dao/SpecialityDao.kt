package com.develop.grizzzly.pediatry.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.develop.grizzzly.pediatry.network.model.Speciality

@Dao
interface SpecialityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSpeciality(s: List<Speciality>)

    @Query("SELECT * FROM speciality WHERE main = 1")
    suspend fun getMainSpecs(): List<Speciality>

    @Query("SELECT * FROM speciality WHERE main = 0")
    suspend fun getExtraSpecs(): List<Speciality>
}