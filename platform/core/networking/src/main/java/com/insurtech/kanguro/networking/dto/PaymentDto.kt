package com.insurtech.kanguro.networking.dto

import com.insurtech.kanguro.common.enums.PolicyInvoiceInterval

data class PaymentDto(
    val firstPayment: Float? = null,
    val totalPayment: Float? = null,
    val invoiceInterval: PolicyInvoiceInterval? = null,
    val recurringPayment: Float? = null
)
