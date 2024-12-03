package com.insurtech.kanguro.designsystem.ui.composables.chatbot.model

import com.insurtech.kanguro.common.date.DateUtils.toLongFormatDate
import java.math.BigDecimal
import java.util.Date

data class ClaimSummaryModel(
    val claimSummaryType: ClaimSummaryType,
    val policyProductType: ProductType,
    val policyStartDate: Date,
    val deductible: BigDecimal,
    val contactPhone: String,
    val incidentDate: Date,
    val injuryModel: ClaimSummaryInjuryModel? = null,
    val itemReportedModel: ClaimSummaryItemReportedModel? = null
) {
    val formattedPolicyStartDate: String
        get() = policyStartDate.toLongFormatDate()

    val formattedIncidentDate: String
        get() = incidentDate.toLongFormatDate()
}
