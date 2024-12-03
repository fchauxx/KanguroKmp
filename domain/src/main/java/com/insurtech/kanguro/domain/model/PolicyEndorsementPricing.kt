package com.insurtech.kanguro.domain.model

data class PolicyEndorsementPricing(
    val billingCycle: InvoiceInterval,
    val currentPolicyValue: Double,
    val endorsementPolicyValue: Double,
    val billingCycleCurrentPolicyValue: Double,
    val billingCycleEndorsementPolicyValue: Double,
    val policyPriceDifferenceValue: Double,
    val billingCyclePolicyPriceDifferenceValue: Double
)
