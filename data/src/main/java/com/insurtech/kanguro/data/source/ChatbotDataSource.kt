package com.insurtech.kanguro.data.source

import com.insurtech.kanguro.domain.model.ChatbotStepModel
import com.insurtech.kanguro.domain.model.Journey

interface ChatbotDataSource {

    suspend fun postChatbot(journey: Journey, data: Map<String, Any?>): String

    suspend fun postChatbotSessionId(sessionId: String, input: Any?): ChatbotStepModel?
}
