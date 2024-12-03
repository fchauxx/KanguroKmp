package com.insurtech.kanguro.domain.model

data class PolicyAdditionalCoverageModel(
    val type: AdditionalCoverageType,
    val coverageLimit: Double,
    val deductibleLimit: Double
)
