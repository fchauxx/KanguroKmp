package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain

import androidx.annotation.DrawableRes
import com.insurtech.kanguro.designsystem.R

enum class PetType(@DrawableRes val icon: Int) {
    dog(R.drawable.ic_dog),
    cat(R.drawable.ic_cat)
}
