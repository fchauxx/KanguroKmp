package com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain

import androidx.annotation.DrawableRes
import com.insurtech.kanguro.designsystem.R

enum class PetType(@DrawableRes val placeHolder: Int) {
    Cat(R.drawable.placeholder_cat_picture),
    Dog(R.drawable.placeholder_dog_picture)
}
