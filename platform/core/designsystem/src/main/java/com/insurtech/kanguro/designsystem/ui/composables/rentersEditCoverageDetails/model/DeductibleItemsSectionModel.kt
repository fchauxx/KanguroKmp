package com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model

import java.math.BigDecimal

data class DeductibleItemsSectionModel(
    val medicalPaymentsPrice: BigDecimal?,
    val hurricaneDeductible: BigDecimal?
)
