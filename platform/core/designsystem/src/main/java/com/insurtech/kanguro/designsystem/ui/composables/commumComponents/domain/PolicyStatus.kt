package com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryDarkest

enum class PolicyStatus(@StringRes val titleRes: Int, val color: Color) {
    ACTIVE(R.string.renters_status_active, PrimaryDarkest),
    CANCELED(R.string.renters_status_canceled, PrimaryDarkest),
    TERMINATED(R.string.renters_status_terminated, PrimaryDarkest),
    PENDING(R.string.renters_status_pending, PrimaryDarkest)
}
