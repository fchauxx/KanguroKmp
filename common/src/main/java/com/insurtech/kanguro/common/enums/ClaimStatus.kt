package com.insurtech.kanguro.common.enums

import androidx.annotation.StringRes
import com.insurtech.kanguro.common.R

enum class ClaimStatus(@StringRes val value: Int, val stringValue: String) {
    Submitted(R.string.claim_status_submitted, "Submitted"),
    Assigned(R.string.claim_status_assigned, "Assigned"), // Created based on UI, should match backend
    Approved(R.string.claim_status_approved, "Approved"),
    InReview(R.string.claim_status_in_review, "InReview"), // Created based on UI, should match backend
    Draft(R.string.claim_status_draft, "Draft"),
    Closed(R.string.claim_status_closed, "Closed"),
    Deleted(R.string.claim_status_deleted, "Deleted"),
    Denied(R.string.claim_status_denied, "Denied"),
    Paid(R.string.claim_status_approved_paid, "Paid"),
    Unknown(R.string.claim_status_unknown, "Unknown"),
    PendingMedicalHistory(R.string.claim_status_pending_medical_history, "PendingMedicalHistory"),
    MedicalHistoryInReview(R.string.claim_status_medical_history_in_review, "MedicalHistoryInReview");

    companion object {
        fun fromString(value: String): ClaimStatus {
            return values().firstOrNull { it.stringValue == value } ?: Unknown
        }
    }
}
