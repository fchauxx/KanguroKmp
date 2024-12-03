package com.insurtech.kanguro.networking.dto

import java.util.Date

data class RentersPolicyViewModelDto(
    val id: String? = null,
    val policyExternalId: Int? = null,
    val dwellingType: DwellingTypeDto? = null,
    val address: AddressModelDto? = null,
    val status: PolicyStatusDto? = null,
    val createdAt: Date? = null,
    val startAt: Date? = null,
    val endAt: Date? = null,
    val planSummary: PolicyPlanSummaryModelDto? = null,
    val additionalCoverages: List<PolicyAdditionalCoverageModelDto>? = null,
    val payment: PolicyPricingPaymentSimulationDto? = null,
    val onboardingCompleted: Boolean? = null,
    val isInsuranceRequired: Boolean? = null
)
