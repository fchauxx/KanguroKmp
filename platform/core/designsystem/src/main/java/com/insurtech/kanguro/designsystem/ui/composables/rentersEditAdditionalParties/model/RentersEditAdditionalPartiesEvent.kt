package com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalParties.model

sealed class RentersEditAdditionalPartiesEvent {
    object OnClosePressed : RentersEditAdditionalPartiesEvent()

    object OnAddPartyPressed : RentersEditAdditionalPartiesEvent()

    object OnSubmitEdition : RentersEditAdditionalPartiesEvent()

    data class OnEditPressed(val partyId: String) : RentersEditAdditionalPartiesEvent()

    data class OnDeletePressed(val partyId: String) : RentersEditAdditionalPartiesEvent()

    object OnDeleteConfirmationPressed : RentersEditAdditionalPartiesEvent()

    object OnTryAgainPressed : RentersEditAdditionalPartiesEvent()
}
