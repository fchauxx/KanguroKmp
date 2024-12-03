package com.insurtech.kanguro.domain.model

import java.util.Date

data class CloudDocument(
    val userId: String? = null,
    val pets: List<CloudPet>? = null,
    val renters: List<CloudRenters>? = null
)

data class CloudPet(
    val id: Long? = null,
    val name: String? = null,
    val cloudDocumentPolicies: List<CloudDocumentPolicy>? = null
)

data class CloudDocumentPolicy(
    val id: String? = null,
    val ciId: Long? = null,
    val policyStartDate: Date? = null,
    val policyAttachments: List<PolicyAttachment>? = null,
    val policyDocuments: List<PolicyDocument>? = null,
    val claimDocuments: List<CloudClaimDocument>? = null
)

data class CloudClaimDocument(
    val claimId: String? = null,
    val claimPrefixId: String? = null,
    val claimDocuments: List<ClaimDocument>? = null
)
