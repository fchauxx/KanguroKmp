package com.insurtech.kanguro.core.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.core.api.bodies.ChatbotNextStepBody
import com.insurtech.kanguro.core.api.bodies.ChatbotSessionStartBody
import com.insurtech.kanguro.core.api.responses.PetPolicyResponse
import com.insurtech.kanguro.domain.chatbot.ChatInteractionStep
import com.insurtech.kanguro.domain.chatbot.ChatSessionInfo
import com.insurtech.kanguro.domain.chatbot.enums.SessionStatus
import com.insurtech.kanguro.networking.dto.ErrorDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface KanguroChatbotApiService {

    @POST("api/chatbot/session/start")
    suspend fun chatbotSessionStart(
        @Body body: ChatbotSessionStartBody
    ): NetworkResponse<ChatInteractionStep, ErrorDto>

    @POST("api/chatbot/next-step")
    suspend fun chatbotSessionNextStep(
        @Body body: ChatbotNextStepBody
    ): NetworkResponse<ChatInteractionStep, ErrorDto>

    @GET("api/chatbot/session")
    suspend fun chatbotOngoingSession(
        @Query("userId") userId: String,
        @Query("status") status: SessionStatus
    ): NetworkResponse<List<ChatSessionInfo>, ErrorDto>

    @GET("api/chatbot/session/{sessionId}")
    suspend fun getPetPolicy(
        @Path("sessionId") sessionId: String
    ): NetworkResponse<PetPolicyResponse, ErrorDto>
}
