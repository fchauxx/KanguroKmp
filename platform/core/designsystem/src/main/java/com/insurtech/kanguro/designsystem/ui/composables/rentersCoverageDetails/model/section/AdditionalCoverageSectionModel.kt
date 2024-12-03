package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section

import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PolicyStatus
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.AdditionalCoverageItemModel

data class AdditionalCoverageSectionModel(
    val additionalCoverages: List<AdditionalCoverageItemModel> = emptyList(),
    val policyStatus: PolicyStatus = PolicyStatus.ACTIVE,
    val isError: Boolean = false
)
