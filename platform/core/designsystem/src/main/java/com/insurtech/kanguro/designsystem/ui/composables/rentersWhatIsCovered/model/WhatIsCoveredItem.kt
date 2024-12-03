package com.insurtech.kanguro.designsystem.ui.composables.rentersWhatIsCovered.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.insurtech.kanguro.designsystem.R

enum class WhatIsCoveredItem(@StringRes val title: Int, @DrawableRes val icon: Int) {
    Theft(
        R.string.renters_what_is_covered_item_theft,
        R.drawable.ic_renters_covered_item_theft
    ),
    Fire(
        R.string.renters_what_is_covered_item_fire,
        R.drawable.ic_renters_covered_item_fire
    ),
    Smoke(
        R.string.renters_what_is_covered_item_smoke,
        R.drawable.ic_renters_covered_item_smoke
    ),
    Hail(
        R.string.renters_what_is_covered_item_hail,
        R.drawable.ic_renters_covered_item_hail
    ),
    Lightning(
        R.string.renters_what_is_covered_item_lightning,
        R.drawable.ic_renters_covered_item_lightning
    ),
    WindStorm(
        R.string.renters_what_is_covered_item_windstorm,
        R.drawable.ic_renters_covered_item_windstorm
    ),
    Vandalism(
        R.string.renters_what_is_covered_item_vandalism,
        R.drawable.ic_renters_covered_item_vandalism
    ),
    Explosion(
        R.string.renters_what_is_covered_item_explosion,
        R.drawable.ic_renters_covered_item_explosion
    )
}
