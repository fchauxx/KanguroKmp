package com.insurtech.kanguro.core.utils

import com.insurtech.kanguro.R
import com.insurtech.kanguro.common.enums.ClaimStatus
import com.insurtech.kanguro.domain.coverage.Claim

fun Claim.getTextColor(): Int {
    return if (this.hasPendingCommunications == true || this.status == ClaimStatus.PendingMedicalHistory) {
        R.color.secondary_darkest
    } else {
        R.color.white
    }
}

fun Claim.getBackgroundColor(): Int {
    return if (this.hasPendingCommunications == true || this.status == ClaimStatus.PendingMedicalHistory) {
        R.drawable.in_review_background
    } else {
        this.status!!.getBackground()
    }
}
