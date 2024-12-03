package com.insurtech.kanguro.domain.model

import com.insurtech.kanguro.common.enums.ClaimStatus
import java.util.Date

data class ClaimDirectPaymentResponse(
    val id: String,
    val petId: Int,
    val type: ClaimType,
    val status: ClaimStatus,
    val decision: String? = null,
    val createdAt: Date,
    val updatedAt: Date,
    val invoiceDate: Date,
    val description: String,
    val prefixId: String? = null,
    val amount: Double,
    val amountPaid: Double? = null,
    val amountTransferred: Double? = null,
    val deductibleContributionAmount: Double? = null,
    val copayAmount: Double? = null,
    val claimFeedback: ClaimFeedback? = null,
    val chatbotSessionsIds: List<String>? = null,
    val pet: Pet? = null,
    val hasPendingCommunications: Boolean? = null,
    val reimbursementProcess: ReimbursementProcess,
    val veterinarianId: Int? = null,
    val fileUrl: String
)
