package com.insurtech.kanguro.networking.dto

import java.util.Date

data class AdditionalPartyDto(
    val id: String? = null,
    val type: AdditionalPartyTypeDto? = null,
    val fullName: String? = null,
    val email: String? = null,
    val birthDate: Date? = null,
    val address: AddressModelDto? = null
)
