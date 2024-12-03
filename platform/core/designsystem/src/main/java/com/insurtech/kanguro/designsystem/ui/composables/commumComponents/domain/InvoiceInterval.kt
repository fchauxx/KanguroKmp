package com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain

import androidx.annotation.StringRes
import com.insurtech.kanguro.designsystem.R

enum class InvoiceInterval(@StringRes val shortText: Int) {

    YEARLY(R.string.yearly_short),
    QUARTERLY(R.string.quarterly_short),
    MONTHLY(R.string.monthly_short)
}
