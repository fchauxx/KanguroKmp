package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model

import java.math.BigDecimal

data class AdditionalCoverageItemModel(
    val type: AdditionalCoverageItemTypeModel,
    val coverageLimit: BigDecimal?,
    val deductible: BigDecimal?,
    val intervalTotal: BigDecimal,
    val isActive: Boolean = false
)
