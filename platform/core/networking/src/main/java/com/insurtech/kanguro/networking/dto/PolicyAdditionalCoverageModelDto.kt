package com.insurtech.kanguro.networking.dto

data class PolicyAdditionalCoverageModelDto(

    val type: AdditionalCoverageTypeDto? = null,
    val coverageLimit: Double? = null,
    val deductibleLimit: Double? = null
)
