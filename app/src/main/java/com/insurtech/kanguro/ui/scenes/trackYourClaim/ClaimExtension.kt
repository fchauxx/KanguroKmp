package com.insurtech.kanguro.ui.scenes.trackYourClaim

import com.insurtech.kanguro.common.date.DateUtils
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetType
import com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims.model.ClaimTrackerCardModel
import com.insurtech.kanguro.domain.model.Claim
import java.text.NumberFormat
import java.util.Currency
import com.insurtech.kanguro.common.enums.ClaimStatus as ClaimStatusDomain
import com.insurtech.kanguro.common.enums.PetType as PetTypeDomain
import com.insurtech.kanguro.domain.coverage.Claim as OldClaim

private const val CLAIM_DATE_FORMAT = "MMM dd, yyyy"
private const val CLAIM_AMOUNT_CURRENCY = "USD"

fun Claim.toTrackerCardModel(): ClaimTrackerCardModel? {
    return ClaimTrackerCardModel(
        id = this.id ?: return null,
        petName = this.pet?.name ?: return null,
        petType = when (this.pet?.type) {
            PetTypeDomain.Dog -> PetType.Cat
            PetTypeDomain.Cat -> PetType.Dog
            else -> PetType.Dog
        },
        claimType = this.type ?: return null,
        claimStatus = when (this.status) {
            ClaimStatusDomain.Submitted -> com.insurtech.kanguro.common.enums.ClaimStatus.Submitted
            ClaimStatusDomain.Assigned -> com.insurtech.kanguro.common.enums.ClaimStatus.Assigned
            ClaimStatusDomain.Approved -> com.insurtech.kanguro.common.enums.ClaimStatus.Approved
            ClaimStatusDomain.InReview -> com.insurtech.kanguro.common.enums.ClaimStatus.InReview
            ClaimStatusDomain.Draft -> com.insurtech.kanguro.common.enums.ClaimStatus.Draft
            ClaimStatusDomain.Closed -> com.insurtech.kanguro.common.enums.ClaimStatus.Closed
            ClaimStatusDomain.Deleted -> com.insurtech.kanguro.common.enums.ClaimStatus.Deleted
            ClaimStatusDomain.Denied -> com.insurtech.kanguro.common.enums.ClaimStatus.Denied
            ClaimStatusDomain.Paid -> com.insurtech.kanguro.common.enums.ClaimStatus.Paid
            ClaimStatusDomain.Unknown -> com.insurtech.kanguro.common.enums.ClaimStatus.Unknown
            ClaimStatusDomain.PendingMedicalHistory -> com.insurtech.kanguro.common.enums.ClaimStatus.PendingMedicalHistory
            ClaimStatusDomain.MedicalHistoryInReview -> com.insurtech.kanguro.common.enums.ClaimStatus.MedicalHistoryInReview
            else -> com.insurtech.kanguro.common.enums.ClaimStatus.Unknown
        },
        claimLastUpdated = this.updatedAt?.let { DateUtils.getEnglishFormattedLocalDate(it, CLAIM_DATE_FORMAT) } ?: return null,
        claimAmount = this.amount?.let { convertToCurrency(it) } ?: return null,
        claimAmountPaid = this.amountPaid?.let { convertToCurrency(it) } ?: return null,
        reimbursementProcess = this.reimbursementProcess ?: return null,
        claimStatusDescription = this.statusDescription?.description,
        petPictureUrl = this.pet?.petPictureUrl
    )
}

private fun convertToCurrency(amount: Double): String {
    val numberFormat = NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 2
        currency = Currency.getInstance(CLAIM_AMOUNT_CURRENCY)
    }

    return numberFormat.format(amount)
}

// This method must be temporary until the old claim is removed from the old domain package
fun Claim.toOldClaim(): OldClaim {
    return OldClaim(
        id = this.id,
        pet = this.pet,
        type = this.type,
        status = this.status,
        chatbotSessionsIds = this.chatbotSessionsIds,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        invoiceDate = this.invoiceDate,
        amount = this.amount,
        amountPaid = this.amountPaid,
        amountTransferred = this.amountTransferred,
        description = this.description,
        decision = this.decision,
        hasPendingCommunications = false,
        prefixId = this.prefixId,
        deductibleContributionAmount = this.deductibleContributionAmount,
        reimbursementProcess = this.reimbursementProcess
    )
}
