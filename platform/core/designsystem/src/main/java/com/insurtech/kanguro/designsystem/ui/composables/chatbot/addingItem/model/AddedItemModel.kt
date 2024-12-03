package com.insurtech.kanguro.designsystem.ui.composables.chatbot.addingItem.model

import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.SelectedFileUi

data class AddedItemModel(
    val item: String? = null,
    val value: String? = null,
    val itemSelectedPicture: SelectedFileUi? = null,
    val itemReceiptPicture: SelectedFileUi? = null
)
