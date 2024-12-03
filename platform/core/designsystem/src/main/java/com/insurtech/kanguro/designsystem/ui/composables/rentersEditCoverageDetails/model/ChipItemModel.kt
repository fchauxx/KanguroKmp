package com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model

import java.math.BigDecimal

data class ChipItemModel(
    val id: String,
    val value: BigDecimal = BigDecimal.ZERO,
    val isMostPopular: Boolean = false,
    val isSelected: Boolean = false
)
