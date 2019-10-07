package com.develop.grizzzly.pediatry.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profile constructor(@PrimaryKey val saveId : Long = 0,
                               val name : String,
                               val lastname : String,
                               val middlename : String,
                               val email : String,
                               val country : String,
                               val city : String,
                               val phone : String,
                               val mainSpecialtyId : Long,
                               val additionalSpecialty1Id : Long?,
                               val additionalSpecialty2Id : Long?,
                               val avatar : String?,
                               val fullCity : String?,
                               val kladrId: String?
                               )