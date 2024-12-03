package com.insurtech.kanguro.networking.api

import com.insurtech.kanguro.networking.dto.ChatbotBodyDto
import com.insurtech.kanguro.networking.dto.ChatbotDto
import com.insurtech.kanguro.networking.dto.ChatbotStepInputModelBodyDto
import com.insurtech.kanguro.networking.dto.ChatbotStepResponseModelDto
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface KanguroChatbotVersionedApiService {
    @POST("api/v{version}/Chatbot")
    suspend fun postChatbot(
        @Path("version") version: Int,
        @Body body: ChatbotBodyDto
    ): ChatbotDto

    @POST("api/v{version}/Chatbot/{sessionId}")
    suspend fun postChatbotSessionId(
        @Path("version") version: Int,
        @Path("sessionId") sessionId: String,
        @Body body: ChatbotStepInputModelBodyDto
    ): ChatbotStepResponseModelDto
}
