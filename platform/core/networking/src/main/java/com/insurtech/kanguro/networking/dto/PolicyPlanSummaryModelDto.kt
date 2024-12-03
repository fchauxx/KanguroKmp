package com.insurtech.kanguro.networking.dto

data class PolicyPlanSummaryModelDto(
    val liability: PlanSummaryItemDto? = null,
    val deductible: PlanSummaryItemDto? = null,
    val personalProperty: PlanSummaryItemDto? = null,
    val lossOfUse: PlanSummaryItemDto? = null
)
