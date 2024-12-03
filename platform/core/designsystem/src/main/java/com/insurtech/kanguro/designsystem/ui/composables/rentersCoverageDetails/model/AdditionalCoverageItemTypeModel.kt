package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.insurtech.kanguro.designsystem.R

enum class AdditionalCoverageItemTypeModel(@DrawableRes val icon: Int) {
    REPLACEMENT_COST(icon = R.drawable.ic_replacement_cost),
    WATER_SEWER_BACKUP(icon = R.drawable.ic_water_sewer_backup),
    FRAUD_PROTECTION(icon = R.drawable.ic_fraud_protection)
}

@StringRes
fun AdditionalCoverageItemTypeModel.getInfoDialogTitle(): Int {
    return when (this) {
        AdditionalCoverageItemTypeModel.REPLACEMENT_COST -> R.string.replacement_cost
        AdditionalCoverageItemTypeModel.WATER_SEWER_BACKUP -> R.string.water_sewer_backup
        AdditionalCoverageItemTypeModel.FRAUD_PROTECTION -> R.string.fraud_protection
    }
}

@StringRes
fun AdditionalCoverageItemTypeModel.getInfoDialogContent(): Int {
    return when (this) {
        AdditionalCoverageItemTypeModel.REPLACEMENT_COST -> R.string.replacement_cost_content
        AdditionalCoverageItemTypeModel.WATER_SEWER_BACKUP -> R.string.water_sewer_backup_content
        AdditionalCoverageItemTypeModel.FRAUD_PROTECTION -> R.string.fraud_protection_content
    }
}
