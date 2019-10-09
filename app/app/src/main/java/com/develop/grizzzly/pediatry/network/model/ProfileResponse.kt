package com.develop.grizzzly.pediatry.network.model

data class ProfileResponse constructor(
    val id: Long,
    val email: String,
    val password: String?,
    val name: String,
    val lastname: String,
    val middlename: String?,
    val city: CityResponse,
    val phone: String,
    val mainSpecialtyId: Long,
    val additionalSpecialty1Id: Long?,
    val additionalSpecialty2Id: Long?,
    val avatar: String?,
    val uploadedAvatar: String?
) {
    fun convert(): Profile =
        Profile(
            0,
            name,
            lastname,
            middlename ?: "",
            email,
            city.name,
            city.kladrId,
            city.country,
            city.fullName,
            phone,
            mainSpecialtyId,
            additionalSpecialty1Id,
            additionalSpecialty1Id,
            avatar
        )
}