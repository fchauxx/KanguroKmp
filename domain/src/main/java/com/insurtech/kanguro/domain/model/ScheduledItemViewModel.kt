package com.insurtech.kanguro.domain.model

data class ScheduledItemViewModel(
    val id: String?,
    val name: String,
    val type: String?,
    val valuation: Double?,
    val images: List<ScheduledItemImage>?
)
