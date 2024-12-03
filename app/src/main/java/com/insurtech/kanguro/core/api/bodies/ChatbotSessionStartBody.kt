package com.insurtech.kanguro.core.api.bodies

import androidx.annotation.Keep
import com.insurtech.kanguro.domain.chatbot.enums.SessionType

@Keep
data class ChatbotSessionStartBody(
    val type: SessionType,
    val petId: Long? = null
)
