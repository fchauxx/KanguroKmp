package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section

import java.math.BigDecimal

data class ScheduledItemsSectionModel(
    val totalValue: BigDecimal = BigDecimal.ZERO,
    val isError: Boolean = false
)
