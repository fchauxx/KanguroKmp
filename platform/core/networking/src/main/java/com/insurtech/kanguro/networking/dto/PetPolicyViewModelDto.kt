package com.insurtech.kanguro.networking.dto

import com.insurtech.kanguro.common.enums.PolicyStatus
import com.squareup.moshi.Json
import java.util.Date

data class PetPolicyViewModelDto(
    val id: String? = null,
    val policyExternalId: Int? = null,
    val petId: Long? = null,
    val policyOfferId: Int? = null,
    @Json(name = "deductable")
    val deductible: DeductibleDto? = null,
    val sumInsured: AmountInfoDto? = null,
    val payment: PaymentDto? = null,
    val preventive: Boolean? = null,
    val waitingPeriod: Date? = null,
    val waitingPeriodRemainingDays: Int? = null,
    @Json(name = "reimbursment")
    val reimbursement: ReimbursementDto? = null,
    val startDate: Date? = null,
    val endDate: Date? = null,
    val status: PolicyStatus? = null,
    val isFuture: Boolean? = null
)
