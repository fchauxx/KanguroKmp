package com.insurtech.kanguro.domain.billingPreferences

data class BillingHistoryItem(
    val name: String,
    val value: Double,
    val monthlyGoodBoy: Double,
    val preventiveAndWellness: Double,
    val creditCard: String
)
