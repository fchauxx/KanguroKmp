package com.insurtech.kanguro.core.api.responses

import com.insurtech.kanguro.common.enums.PolicyStatus
import com.insurtech.kanguro.domain.model.AmountInfo
import com.insurtech.kanguro.domain.model.Deductible
import com.insurtech.kanguro.domain.model.Payment
import com.insurtech.kanguro.domain.model.Reimbursement
import java.util.*

data class PolicyResponse(
    val deductable: Deductible? = null,
    val sumInsured: AmountInfo? = null, // annual limit
    val payment: Payment? = null,
    val preventive: Boolean? = null,
    val waitingPeriod: Date? = null,
    val waitingPeriodRemainingDays: Int? = null,
    val petId: Long? = null,
    val id: String? = null,
    val reimbursment: Reimbursement? = null,
    val startDate: Date? = null,
    val endDate: Date? = null,
    val status: PolicyStatus? = null,
    val policyExternalId: Int? = null,
    val policyOfferId: Int? = null,
    val isFuture: Boolean? = null
)
