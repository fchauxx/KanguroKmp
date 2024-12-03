package com.insurtech.kanguro.networking.dto

data class AdditionalCoverageDto(
    val type: AdditionalCoverageTypeDto? = null,
    val total: Double? = null,
    val intervalTotal: Double? = null,
    val coverageLimit: Double? = null,
    val deductibleLimit: Double? = null,
    val label: String? = null
)
