package com.insurtech.kanguro.core.api.responses

import com.insurtech.kanguro.common.enums.ClaimStatus
import java.util.*

data class ClaimResponse(
    val id: String? = null,
    val petId: Long? = null,
    val type: String? = null,
    val status: ClaimStatus? = null,
    val createdAt: Date? = null,
    val description: String? = null,
    val amount: Double? = null,
    val chatbotSessionsIds: List<String>? = null
)
