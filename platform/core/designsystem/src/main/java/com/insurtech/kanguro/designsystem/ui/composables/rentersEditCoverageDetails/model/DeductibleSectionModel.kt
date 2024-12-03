package com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model

data class DeductibleSectionModel(
    val items: List<ChipItemModel> = emptyList(),
    var isInputEnabled: Boolean = true
)
