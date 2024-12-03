package com.insurtech.kanguro.networking.dto

data class CloudClaimDocumentDto(
    val claimId: String? = null,
    val claimPrefixId: String? = null,
    val claimDocuments: List<ClaimDocumentDto>? = null
)
