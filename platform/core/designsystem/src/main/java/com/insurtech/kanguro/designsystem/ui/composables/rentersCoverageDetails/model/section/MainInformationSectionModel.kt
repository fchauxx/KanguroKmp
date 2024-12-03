package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section

import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PolicyStatus
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PlanSummaryCardModel
import com.insurtech.kanguro.domain.model.PolicyDocument
import java.math.BigDecimal

data class MainInformationSectionModel(
    val planSummary: PlanSummaryCardModel = PlanSummaryCardModel(
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO
    ),
    var documents: List<PolicyDocument> = emptyList(),
    val renewDate: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val policyStatus: PolicyStatus = PolicyStatus.ACTIVE,
    val isError: Boolean = false
)
