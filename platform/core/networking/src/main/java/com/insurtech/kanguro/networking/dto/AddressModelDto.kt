package com.insurtech.kanguro.networking.dto

data class AddressModelDto(
    val complement: String? = null,
    val streetNumber: String? = null,
    val streetName: String? = null,
    val city: String? = null,
    val state: String? = null,
    val zipCode: String? = null
)
