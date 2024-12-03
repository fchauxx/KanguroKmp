package com.insurtech.kanguro.networking.dto

data class ScheduledItemEndorsementPricingDto(
    val billingCycle: InvoiceIntervalDto?,
    val currentPolicyValue: Double?,
    val endorsementPolicyValue: Double?,
    val billingCycleCurrentPolicyValue: Double?,
    val billingCycleEndorsementPolicyValue: Double?,
    val policyPriceDifferenceValue: Double?,
    val billingCyclePolicyPriceDifferenceValue: Double?,
    val scheduledItem: ScheduledItemDto?
)
