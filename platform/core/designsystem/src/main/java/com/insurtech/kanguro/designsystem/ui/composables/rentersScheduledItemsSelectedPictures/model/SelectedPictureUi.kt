package com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsSelectedPictures.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.insurtech.kanguro.domain.model.ScheduledItemImageType
import com.insurtech.kanguro.domain.model.ScheduledItemInputImage

data class SelectedPictureUi(
    val id: Int,
    val type: ScheduledItemImageType? = null,
    val url: String? = null
) {
    var selected by mutableStateOf(false)
}

fun SelectedPictureUi.toScheduledItemInputImage(): ScheduledItemInputImage {
    return ScheduledItemInputImage(
        fileId = id,
        type = type
    )
}
