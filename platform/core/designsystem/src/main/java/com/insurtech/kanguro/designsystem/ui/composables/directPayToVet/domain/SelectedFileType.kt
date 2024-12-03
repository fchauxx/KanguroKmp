package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain

import androidx.annotation.DrawableRes
import com.insurtech.kanguro.designsystem.R

enum class SelectedFileType(@DrawableRes val icon: Int) {
    Image(R.drawable.ic_image_place_holder),
    File(R.drawable.ic_document_text),
    Video(R.drawable.ic_document_video)
}
