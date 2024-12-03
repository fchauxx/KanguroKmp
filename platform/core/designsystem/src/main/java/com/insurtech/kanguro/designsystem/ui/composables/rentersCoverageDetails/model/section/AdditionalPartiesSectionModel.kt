package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section

import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PolicyStatus
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PartyItemModel

data class AdditionalPartiesSectionModel(
    val additionalParties: List<PartyItemModel> = emptyList(),
    val policyStatus: PolicyStatus = PolicyStatus.ACTIVE,
    val isError: Boolean = false
)
