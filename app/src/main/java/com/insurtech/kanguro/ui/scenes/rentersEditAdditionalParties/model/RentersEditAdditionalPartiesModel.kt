package com.insurtech.kanguro.ui.scenes.rentersEditAdditionalParties.model

import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PartyItemModel

data class RentersEditAdditionalPartiesModel(
    val partyItemList: List<PartyItemModel> = emptyList(),
    val isShowingDeleteDialog: Boolean = false
)
