package com.insurtech.kanguro.domain.model

import java.util.Date

data class AdditionalParty(
    val id: String,
    val type: AdditionalPartyType,
    val fullName: String,
    val email: String,
    val birthDate: Date?,
    val address: AddressModel?
)
