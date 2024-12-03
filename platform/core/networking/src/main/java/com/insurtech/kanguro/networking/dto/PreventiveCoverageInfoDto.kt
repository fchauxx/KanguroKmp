package com.insurtech.kanguro.networking.dto

data class PreventiveCoverageInfoDto(
    val name: String? = null,
    val value: Double? = null,
    val usedValue: Double? = null,
    val remainingValue: Double? = null,
    val annualLimit: Double? = null,
    val remainingLimit: Double? = null,
    val uses: Int? = null,
    val remainingUses: Int? = null,
    val varName: String? = null,
    val usesLimit: Int? = null
)
