package com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model

sealed class RentersEditCoverageDetailsEvent {
    object OnClosePressed : RentersEditCoverageDetailsEvent()

    data class OnYourBelongingsValueChange(val value: Float) : RentersEditCoverageDetailsEvent()

    data class OnLiabilitySelected(val liabilityItemModel: ChipItemModel) :
        RentersEditCoverageDetailsEvent()

    object OnLiabilityInformationPressed : RentersEditCoverageDetailsEvent()

    data class OnDeductibleSelected(val itemModel: ChipItemModel) :
        RentersEditCoverageDetailsEvent()

    object OnDeductibleInformationPressed : RentersEditCoverageDetailsEvent()

    object OnSubmitPressed : RentersEditCoverageDetailsEvent()

    object OnTryAgainPressed : RentersEditCoverageDetailsEvent()
}
