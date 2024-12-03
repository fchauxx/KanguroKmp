package com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalCoverage.domain

import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.AdditionalCoverageItemTypeModel

data class AdditionalCoverageCardModel(
    val type: AdditionalCoverageItemTypeModel,
    val description: String,
    val monthlyPrice: String,
    val isPreviouslySelected: Boolean,
    val isSelected: Boolean
)
