package com.insurtech.kanguro.domain.model

import java.util.Date

data class Reminder(
    val id: String,
    val type: ReminderType,
    val pet: Pet,
    val dueDate: Date?,
    val claimId: String?
)
