package com.insurtech.kanguro.core.repository.chatbot

import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.core.api.bodies.ChatbotSessionStartBody
import com.insurtech.kanguro.core.api.responses.PetPolicyResponse
import com.insurtech.kanguro.domain.chatbot.ChatInteractionStep
import com.insurtech.kanguro.domain.chatbot.ChatSessionInfo
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotAction
import com.insurtech.kanguro.networking.dto.ErrorDto

interface IChatbotRepository {
    suspend fun getOngoingSessions(): NetworkResponse<List<ChatSessionInfo>, ErrorDto>
    suspend fun startSession(sessionStartInfo: ChatbotSessionStartBody): NetworkResponse<ChatInteractionStep, ErrorDto>
    suspend fun nextStep(
        sessionId: String,
        value: String?,
        action: ChatbotAction?
    ): NetworkResponse<ChatInteractionStep, ErrorDto>

    suspend fun nextStep(
        sessionId: String,
        data: ByteArray,
        extension: String,
        action: ChatbotAction?
    ): NetworkResponse<ChatInteractionStep, ErrorDto>

    suspend fun getPetPolicy(sessionId: String): NetworkResponse<PetPolicyResponse, ErrorDto>
}
