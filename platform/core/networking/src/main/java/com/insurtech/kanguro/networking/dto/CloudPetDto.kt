package com.insurtech.kanguro.networking.dto

data class CloudPetDto(
    val id: Long? = null,
    val name: String? = null,
    val cloudDocumentPolicies: List<CloudDocumentPolicyDto>? = null
)
