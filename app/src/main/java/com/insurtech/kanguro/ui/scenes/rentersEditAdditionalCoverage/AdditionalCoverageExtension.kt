package com.insurtech.kanguro.ui.scenes.rentersEditAdditionalCoverage

import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.AdditionalCoverageItemTypeModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalCoverage.domain.AdditionalCoverageCardModel
import com.insurtech.kanguro.domain.model.AdditionalCoverage
import com.insurtech.kanguro.domain.model.AdditionalCoverageType

fun AdditionalCoverage.toUiModel(isPreviouslySelected: Boolean = false): AdditionalCoverageCardModel {
    return AdditionalCoverageCardModel(
        type = when (this.type) {
            AdditionalCoverageType.ReplacementCost -> AdditionalCoverageItemTypeModel.REPLACEMENT_COST
            AdditionalCoverageType.WaterSewerBackup -> AdditionalCoverageItemTypeModel.WATER_SEWER_BACKUP
            AdditionalCoverageType.FraudProtection -> AdditionalCoverageItemTypeModel.FRAUD_PROTECTION
        },
        description = this.label,
        monthlyPrice = "${this.intervalTotal}",
        isPreviouslySelected = isPreviouslySelected,
        isSelected = isPreviouslySelected
    )
}
