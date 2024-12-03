package com.insurtech.kanguro.networking.dto

import java.util.Date

data class ClaimDto(
    val id: String? = null,
    val pet: PetDto? = null,
    val type: ClaimTypeDto? = null,
    val status: String? = null,
    val chatbotSessionsIds: List<String>? = null,
    val createdAt: Date? = null,
    val updatedAt: Date? = null,
    val invoiceDate: Date? = null,
    val amount: Double? = null,
    val amountPaid: Double? = null,
    val amountTransferred: Double? = null,
    val description: String? = null,
    val decision: String? = null,
    val hasPendingCommunications: Boolean? = null,
    val prefixId: String? = null,
    val deductibleContributionAmount: Double? = null,
    val reimbursementProcess: ReimbursementProcessDto? = null,
    val veterinarianId: Int? = null,
    val statusDescription: ClaimStatusDescriptionDto? = null
)
