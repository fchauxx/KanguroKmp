package com.insurtech.kanguro.domain.dashboard

import com.insurtech.kanguro.R

enum class ReminderType(val label: Int) {
    MedicalHistory(R.string.medical_history),
    Claim(R.string.claim),
    PetPicture(R.string.pet_picture)
}
