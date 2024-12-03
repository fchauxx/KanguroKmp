package com.insurtech.kanguro.networking.dto

data class ScheduledItemViewModelDto(
    val id: String?,
    val name: String,
    val type: String?,
    val valuation: Double?,
    val images: List<ScheduledItemImageDto>?
)
