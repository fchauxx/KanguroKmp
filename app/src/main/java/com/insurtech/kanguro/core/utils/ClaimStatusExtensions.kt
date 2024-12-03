package com.insurtech.kanguro.core.utils

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.insurtech.kanguro.R
import com.insurtech.kanguro.common.enums.ClaimStatus

@ColorRes
fun ClaimStatus.getColor(): Int = when (this) {
    ClaimStatus.Submitted -> R.color.tertiary_extra_dark
    ClaimStatus.Approved -> R.color.positive_darkest
    ClaimStatus.Closed -> R.color.negative_darkest
    ClaimStatus.Denied -> R.color.negative_darkest
    ClaimStatus.InReview -> R.color.tertiary_extra_dark
    ClaimStatus.Paid -> R.color.positive_darkest
    else -> R.color.clear
}

@DrawableRes
fun ClaimStatus.getBackground(): Int = when (this) {
    ClaimStatus.Submitted -> R.drawable.submitted_background
    ClaimStatus.InReview -> R.drawable.submitted_background
    ClaimStatus.Closed -> R.drawable.submitted_background
    ClaimStatus.Approved -> R.drawable.approved_background
    ClaimStatus.Denied -> R.drawable.denied_background
    ClaimStatus.Paid -> R.drawable.approved_background
    else -> R.drawable.submitted_background
}

@DrawableRes
fun ClaimStatus.getIcon(): Int? = when (this) {
    ClaimStatus.Paid -> R.drawable.ic_circle_success
    ClaimStatus.Denied -> R.drawable.ic_circle_error
    else -> null
}

@StringRes
fun ClaimStatus.getString(): Int = this.value
