package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model

import java.math.BigDecimal

data class PlanSummaryCardModel(
    val liability: BigDecimal,
    val deductible: BigDecimal,
    val personalProperty: BigDecimal,
    val lossOfUse: BigDecimal
)
