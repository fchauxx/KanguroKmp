package com.insurtech.kanguro.networking.dto

import com.insurtech.kanguro.common.enums.Gender
import com.insurtech.kanguro.common.enums.PetSize
import com.insurtech.kanguro.common.enums.PetType
import java.util.Date

data class PetDto(
    val id: Long? = null,
    val name: String? = null,
    val birthDate: Date? = null,
    val isBirthDateApproximated: Boolean? = null,
    val gender: Gender? = null,
    val type: PetType? = null,
    val size: PetSize? = null,
    val isNeutered: Boolean? = null,
    val preExistingConditions: String? = null,
    val petBreedId: Long? = null,
    val breed: String? = null,
    val zipCode: String? = null,
    val additionalInfoSessionId: String? = null,
    val hasAdditionalInfo: Boolean? = null,
    val petPictureUrl: String? = null,
    val pictureId: Int? = null
)
