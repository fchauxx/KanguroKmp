package com.insurtech.kanguro.domain.coverage

import android.os.Parcelable
import com.insurtech.kanguro.common.enums.ClaimStatus
import com.insurtech.kanguro.domain.model.ClaimType
import com.insurtech.kanguro.domain.model.Pet
import com.insurtech.kanguro.domain.model.ReimbursementProcess
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
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
    val hasPendingCommunications: Boolean? = null,
    val prefixId: String? = null,
    val deductibleContributionAmount: Double? = null,
    val reimbursementProcess: ReimbursementProcess? = null
) : Parcelable {

    fun getStatusAsNumber() = when (status) {
        ClaimStatus.Submitted -> 1
        ClaimStatus.Assigned,
        ClaimStatus.Approved -> 2

        ClaimStatus.InReview -> 3
        else -> 4
    }
}
