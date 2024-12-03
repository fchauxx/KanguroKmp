package com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalCoverage.domain

sealed class RentersEditAdditionalCoverageEvent {

    object OnClosePressed : RentersEditAdditionalCoverageEvent()

    object OnSubmitPressed : RentersEditAdditionalCoverageEvent()

    object OnTryAgainPressed : RentersEditAdditionalCoverageEvent()

    data class OnAdditionalCoveragePressed(val additionalCoverage: AdditionalCoverageCardModel) : RentersEditAdditionalCoverageEvent()
}
