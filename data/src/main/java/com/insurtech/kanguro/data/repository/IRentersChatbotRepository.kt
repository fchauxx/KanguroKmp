package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.domain.model.ChatbotStepModel
import com.insurtech.kanguro.domain.model.Journey
import kotlinx.coroutines.flow.Flow

interface IRentersChatbotRepository {

    suspend fun postChatbot(journey: Journey, data: Map<String, Any?>): Flow<Result<String>>

    suspend fun postChatbotSessionId(
        sessionId: String,
        input: Any?
    ): Flow<Result<ChatbotStepModel>>
}
