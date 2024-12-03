package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section

import androidx.annotation.StringRes
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PolicyStatus
import java.math.BigDecimal

data class PaymentSectionModel(
    val paymentValue: BigDecimal = BigDecimal.ZERO,
    @StringRes val invoiceInterval: Int = R.string.monthly_payment,
    val policyStatus: PolicyStatus = PolicyStatus.ACTIVE,
    val isError: Boolean = false
)
