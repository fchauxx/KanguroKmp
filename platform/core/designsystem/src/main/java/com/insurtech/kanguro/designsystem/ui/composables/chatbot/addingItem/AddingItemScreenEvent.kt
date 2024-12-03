package com.insurtech.kanguro.designsystem.ui.composables.chatbot.addingItem

import android.net.Uri
import com.insurtech.kanguro.domain.model.FilePickerResult

sealed class AddingItemScreenEvent {
    object OnClosePressed : AddingItemScreenEvent()
    object OnDonePressed : AddingItemScreenEvent()
    data class OnItemChanged(val item: String) : AddingItemScreenEvent()
    data class OnValueChanged(val value: String) : AddingItemScreenEvent()
    object OnAddItemPicturePressed : AddingItemScreenEvent()
    object OnAddReceiptPicturePressed : AddingItemScreenEvent()
    object OnDeleteItemPicturePressed : AddingItemScreenEvent()
    object OnDeleteReceiptPicturePressed : AddingItemScreenEvent()
    data class OnPictureSelected(val result: FilePickerResult<Uri>) : AddingItemScreenEvent()
}
