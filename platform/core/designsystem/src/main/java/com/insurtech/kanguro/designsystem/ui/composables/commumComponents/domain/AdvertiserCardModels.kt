package com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain

import androidx.annotation.DrawableRes
import com.insurtech.kanguro.designsystem.R

sealed class AdvertiserCardModels(open val id: String, open val onClick: ((String) -> Unit)?) {
    companion object {
        const val ROAM_ID = "ROAM"
        const val MISSING_PETS_ID = "MISSINGPETS"
    }

    open class AsyncModel(
        override val id: String,
        val image: String,
        override val onClick: ((String) -> Unit)? = null
    ) : AdvertiserCardModels(id, onClick)

    open class PainterModel(
        override val id: String,
        @DrawableRes val image: Int,
        override val onClick: ((String) -> Unit)? = null
    ) : AdvertiserCardModels(id, onClick)

    // Common implementations
    object MissingPets : PainterModel(MISSING_PETS_ID, R.drawable.img_banner_missing_pets)
    object Roam : PainterModel(ROAM_ID, R.drawable.img_banner_advertising)
}
