package com.insurtech.kanguro.domain.model

data class PolicyPlanSummaryModel(
    val liability: PlanSummaryItem,
    val deductible: PlanSummaryItem,
    val personalProperty: PlanSummaryItem,
    val lossOfUse: PlanSummaryItem
)
