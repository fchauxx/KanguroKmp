package com.insurtech.kanguro.domain.model

import com.insurtech.kanguro.common.enums.ClaimStatus
import java.util.Date

data class Claim(
    val id: String? = null,
    val pet: Pet? = null,
    val type: ClaimType? = null,
    val status: ClaimStatus? = null,
    val chatbotSessionsIds: List<String>? = null,
    val createdAt: Date? = null,
    val updatedAt: Date? = null,
    val invoiceDate: Date? = null,
    val amount: Double? = null,
    val amountPaid: Double? = null,
    val amountTransferred: Double? = null,
    val description: String? = null,
    val decision: String? = null,
    val prefixId: String? = null,
    val deductibleContributionAmount: Double? = null,
    val reimbursementProcess: ReimbursementProcess? = null,
    val veterinarianId: Int? = null,
    val statusDescription: ClaimStatusDescription? = null
)
