package com.insurtech.kanguro.common.enums

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.insurtech.kanguro.common.R

enum class PolicyStatus(@StringRes val titleRes: Int, @ColorRes val color: Int) {
    ACTIVE(R.string.active, R.color.policy_status_active),
    CANCELED(R.string.canceled, R.color.policy_status_canceled),
    TERMINATED(R.string.finished, R.color.policy_status_terminated),
    PENDING(R.string.inactive, R.color.policy_status_pending)
}
