package com.insurtech.kanguro.domain.model

import android.os.Parcelable
import com.insurtech.kanguro.common.enums.PolicyStatus
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class PetPolicy(
    val deductible: Deductible? = null,
    val sumInsured: AmountInfo? = null,
    val payment: Payment? = null,
    val pet: Pet? = null,
    val preventive: Boolean? = null,
    val waitingPeriod: Date? = null,
    val waitingPeriodRemainingDays: Int = 0,
    val id: String? = null,
    val reimbursement: Reimbursement? = null,
    val startDate: Date? = null,
    val endDate: Date? = null,
    val status: PolicyStatus? = null,
    val policyExternalId: Int? = null,
    val policyOfferId: Int? = null,
    val isFuture: Boolean? = null
) : Parcelable
