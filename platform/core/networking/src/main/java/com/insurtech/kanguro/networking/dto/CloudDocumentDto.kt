package com.insurtech.kanguro.networking.dto

data class CloudDocumentDto(
    val userId: String? = null,
    val pets: List<CloudPetDto>? = null,
    val renters: List<CloudRentersDto>? = null
)
