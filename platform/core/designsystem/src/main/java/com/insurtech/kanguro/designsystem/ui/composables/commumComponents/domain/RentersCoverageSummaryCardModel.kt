package com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain

import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.DwellingType

data class RentersCoverageSummaryCardModel(
    val id: String,
    val address: String,
    val type: DwellingType,
    val status: CoverageStatusUi
)
