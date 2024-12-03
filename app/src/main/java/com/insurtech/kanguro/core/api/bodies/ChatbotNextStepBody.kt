package com.insurtech.kanguro.core.api.bodies

import androidx.annotation.Keep

@Keep
data class ChatbotNextStepBody(
    val sessionId: String,
    val value: String?,
    val action: String?
)
