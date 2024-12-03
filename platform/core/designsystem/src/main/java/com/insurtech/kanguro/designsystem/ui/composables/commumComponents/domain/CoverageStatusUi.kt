package com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.NegativeDarkest
import com.insurtech.kanguro.designsystem.ui.theme.PositiveDark

enum class CoverageStatusUi(@StringRes val label: Int, val color: Color) {
    Active(R.string.active, PositiveDark),
    Pending(R.string.pending, NegativeDarkest),
    Cancelled(R.string.cancelled, NegativeDarkest),
    Terminated(R.string.terminated, NegativeDarkest)
}
