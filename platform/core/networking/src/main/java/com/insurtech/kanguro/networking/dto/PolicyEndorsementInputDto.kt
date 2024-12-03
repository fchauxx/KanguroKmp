package com.insurtech.kanguro.networking.dto

data class PolicyEndorsementInputDto(
    val deductibleId: Int,
    val liabilityId: Int,
    val personalProperty: Double
)
