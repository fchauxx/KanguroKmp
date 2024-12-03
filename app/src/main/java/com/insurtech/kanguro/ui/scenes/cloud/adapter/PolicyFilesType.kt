package com.insurtech.kanguro.ui.scenes.cloud.adapter

import androidx.annotation.StringRes
import com.insurtech.kanguro.R

enum class PolicyFilesType(@StringRes val res: Int) {
    ClaimDocuments(R.string.claim_documents),
    DigitalVaccineCard(R.string.digital_vaccine_card),
    PolicyDocument(R.string.policy_documents),
    PolicyAttachment(R.string.medical_history) // The file is a medical history
}
