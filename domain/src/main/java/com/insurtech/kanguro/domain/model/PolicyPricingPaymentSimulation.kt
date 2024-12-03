package com.insurtech.kanguro.domain.model

data class PolicyPricingPaymentSimulation(
    val totalFees: Double,
    val totalPayment: Double,
    val invoiceInterval: InvoiceInterval,
    val totalDiscounts: Double,
    val totalPaymentWithoutFees: Double,
    val firstPayment: Double,
    val recurringPayment: Double
)
