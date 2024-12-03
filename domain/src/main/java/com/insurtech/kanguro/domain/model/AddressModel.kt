package com.insurtech.kanguro.domain.model

data class AddressModel(
    val complement: String?,
    val streetNumber: String?,
    val streetName: String?,
    val city: String?,
    val state: String?,
    val zipCode: String?
)
