package com.insurtech.kanguro.networking.dto

data class PolicyPricingPaymentSimulationDto(

    val totalFees: Double? = null,
    val totalPayment: Double? = null,
    val invoiceInterval: InvoiceIntervalDto? = null,
    val totalDiscounts: Double? = null,
    val totalPaymentWithoutFees: Double? = null,
    val firstPayment: Double? = null,
    val recurringPayment: Double? = null
)
