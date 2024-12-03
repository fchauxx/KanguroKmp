package com.insurtech.kanguro.networking.dto

data class PolicyEndorsementPricingDto(
    val billingCycle: InvoiceIntervalDto?,
    val currentPolicyValue: Double?,
    val endorsementPolicyValue: Double?,
    val billingCycleCurrentPolicyValue: Double?,
    val billingCycleEndorsementPolicyValue: Double?,
    val policyPriceDifferenceValue: Double?,
    val billingCyclePolicyPriceDifferenceValue: Double?
)
