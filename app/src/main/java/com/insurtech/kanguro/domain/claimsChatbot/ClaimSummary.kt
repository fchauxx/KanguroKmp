package com.insurtech.kanguro.domain.claimsChatbot

import java.util.*

data class ClaimSummary(
    val pet: String? = null,
    val claim: String? = null,
    val date: Date? = null,
    val attachments: Int? = null,
    val amount: Double? = null,
    val claimId: String? = null,
    val paymentMethod: String? = null
)
