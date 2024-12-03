package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain

import androidx.annotation.StringRes
import com.insurtech.kanguro.designsystem.R

enum class FormClaimType(@StringRes val label: Int) {
    Accident(R.string.accident),
    Illness(R.string.illness)
}
