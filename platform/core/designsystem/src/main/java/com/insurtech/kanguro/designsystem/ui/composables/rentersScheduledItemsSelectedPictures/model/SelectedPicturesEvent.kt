package com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsSelectedPictures.model

import com.insurtech.kanguro.domain.model.ScheduledItemImageType

sealed class SelectedPicturesEvent {
    object Back : SelectedPicturesEvent()
    object Done : SelectedPicturesEvent()
    data class CapturePicture(val type: ScheduledItemImageType) : SelectedPicturesEvent()
    data class SelectPicture(val type: ScheduledItemImageType) : SelectedPicturesEvent()
    data class DeletePicture(val id: Int, val type: ScheduledItemImageType) : SelectedPicturesEvent()
    data class ShowAddPictureBottomSheet(val type: ScheduledItemImageType) : SelectedPicturesEvent()
    object CloseAddPictureBottomSheet : SelectedPicturesEvent()
}
