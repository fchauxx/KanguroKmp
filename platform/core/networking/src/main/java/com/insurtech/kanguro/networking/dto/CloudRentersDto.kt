package com.insurtech.kanguro.networking.dto

data class CloudRentersDto(
    val id: String? = null,
    val name: String? = null,
    val cloudDocumentPolicies: List<CloudDocumentPolicyDto>? = null
)
