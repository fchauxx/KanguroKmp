package com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model

data class LiabilitySectionModel(
    val liabilityItems: List<ChipItemModel> = emptyList(),
    var isInputEnabled: Boolean = true
)
