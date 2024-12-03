package com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims.model

import com.insurtech.kanguro.common.enums.ClaimStatus
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetType
import com.insurtech.kanguro.domain.model.ClaimType
import com.insurtech.kanguro.domain.model.ReimbursementProcess

data class ClaimTrackerCardModel(
    val id: String,
    val petName: String,
    val petType: PetType,
    val claimType: ClaimType,
    val claimStatus: ClaimStatus,
    val claimLastUpdated: String,
    val claimAmount: String,
    val claimAmountPaid: String,
    val reimbursementProcess: ReimbursementProcess,
    val claimStatusDescription: String?,
    val petPictureUrl: String?
)
