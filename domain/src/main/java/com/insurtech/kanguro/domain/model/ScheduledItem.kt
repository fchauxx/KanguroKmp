package com.insurtech.kanguro.domain.model

data class ScheduledItem(
    val type: String,
    val valuation: Double,
    val total: Double,
    val intervalTotal: Double
)
