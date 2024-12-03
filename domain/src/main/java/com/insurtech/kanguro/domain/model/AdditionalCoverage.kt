package com.insurtech.kanguro.domain.model

data class AdditionalCoverage(
    val type: AdditionalCoverageType,
    val total: Double,
    val intervalTotal: Double,
    val coverageLimit: Double? = null,
    val deductibleLimit: Double? = null,
    val label: String
)
