package com.insurtech.kanguro.common.enums

import androidx.annotation.StringRes
import com.insurtech.kanguro.common.R

enum class PolicyInvoiceInterval(@StringRes val invoiceTitle: Int) {
    MONTHLY(R.string.monthly_payment),
    QUARTERLY(R.string.quarterly_payment),
    YEARLY(R.string.yearly_payment)
}
