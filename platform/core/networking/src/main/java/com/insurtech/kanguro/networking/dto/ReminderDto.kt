package com.insurtech.kanguro.networking.dto

import java.util.Date

data class ReminderDto(
    val id: String? = null,
    val userId: String? = null,
    val petId: Long? = null,
    val dueDate: Date? = null,
    val createdAt: Date? = null,
    val type: ReminderTypeDto? = null,
    val claimId: String? = null
)
