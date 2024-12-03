package com.insurtech.kanguro.domain.model

data class CloudRenters(
    val id: String? = null,
    val name: String? = null,
    val cloudDocumentPolicies: List<CloudDocumentPolicy>? = null
)
