package com.insurtech.kanguro.domain.model

data class PolicyEndorsementInput(
    val deductibleId: Int,
    val liabilityId: Int,
    val personalProperty: Double
)
