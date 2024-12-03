package com.insurtech.kanguro.domain.model

import java.util.Date

data class RentersPolicy(
    val id: String,
    val policyExternalId: Int? = null,
    val dwellingType: DwellingType,
    val address: AddressModel,
    val status: PolicyStatus,
    val createdAt: Date,
    val startAt: Date,
    val endAt: Date?,
    val planSummary: PolicyPlanSummaryModel,
    val additionalCoverages: List<PolicyAdditionalCoverageModel>?,
    val payment: PolicyPricingPaymentSimulation,
    val onboardingCompleted: Boolean,
    val isInsuranceRequired: Boolean,
    var documents: List<PolicyDocument> = emptyList()
)
