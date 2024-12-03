package com.insurtech.kanguro.networking.dto

import java.util.Date

data class CloudDocumentPolicyDto(
    val id: String? = null,
    val ciId: Long? = null,
    val policyStartDate: Date? = null,
    val policyAttachments: List<PolicyAttachmentDto>? = null,
    val policyDocuments: List<PolicyDocumentDto>? = null,
    val claimDocuments: List<CloudClaimDocumentDto>? = null
)
