package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section

import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.DwellingType

data class HeaderSectionModel(
    val userName: String = "",
    val address: String = "",
    val dwellingType: DwellingType = DwellingType.SingleFamily,
    val pictureUrl: String = "",
    val isError: Boolean = false,
    val policyNumber: String = ""
)
