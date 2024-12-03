package com.insurtech.kanguro.domain.model

import android.os.Parcelable
import com.insurtech.kanguro.common.enums.PolicyInvoiceInterval
import kotlinx.parcelize.Parcelize

@Parcelize
data class Payment(
    val firstPayment: Float? = null,
    val totalPayment: Float? = null,
    val invoiceInterval: PolicyInvoiceInterval? = null,
    val recurringPayment: Float? = null
) : Parcelable
