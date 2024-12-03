package com.insurtech.kanguro.networking.dto

import java.util.Date

data class ClaimDirectPaymentResponseDto(
    val id: String? = null,
    val petId: Int? = null,
    val type: ClaimTypeDto? = null,
    val status: String? = null,
    val decision: String? = null,
    val createdAt: Date? = null,
    val updatedAt: Date? = null,
    val invoiceDate: Date? = null,
    val description: String? = null,
    val prefixId: String? = null,
    val amount: Double? = null,
    val amountPaid: Double? = null,
    val amountTransferred: Double? = null,
    val deductibleContributionAmount: Double? = null,
    val copayAmount: Double? = null,
    val claimFeedback: ClaimFeedbackDto? = null,
    val chatbotSessionsIds: List<String>? = null,
    val pet: PetDto? = null,
    val hasPendingCommunications: Boolean? = null,
    val reimbursementProcess: ReimbursementProcessDto? = null,
    val veterinarianId: Int? = null,
    val fileUrl: String? = null
)
