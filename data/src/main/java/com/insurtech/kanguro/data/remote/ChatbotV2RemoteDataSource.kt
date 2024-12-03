package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.mapper.ChatbotMapper.mapChatbotDtoToString
import com.insurtech.kanguro.data.mapper.ChatbotMapper.mapChatbotStepResponseModelDto
import com.insurtech.kanguro.data.mapper.ChatbotMapper.mapJourneyToJourneyDto
import com.insurtech.kanguro.data.source.ChatbotDataSource
import com.insurtech.kanguro.domain.model.ChatbotStepModel
import com.insurtech.kanguro.domain.model.Journey
import com.insurtech.kanguro.networking.api.KanguroChatbotVersionedApiService
import com.insurtech.kanguro.networking.dto.ChatbotBodyDto
import com.insurtech.kanguro.networking.dto.ChatbotStepInputModelBodyDto
import com.insurtech.kanguro.networking.transformer.managedExecution
import javax.inject.Inject

class ChatbotV2RemoteDataSource @Inject constructor(
    private val chatbotVersionedApiService: KanguroChatbotVersionedApiService
) : ChatbotDataSource {

    private val apiVersion = 2

    override suspend fun postChatbot(journey: Journey, data: Map<String, Any?>): String =
        managedExecution {
            mapChatbotDtoToString(
                chatbotVersionedApiService.postChatbot(
                    apiVersion,
                    ChatbotBodyDto(mapJourneyToJourneyDto(journey), data)
                )
            )
        }

    override suspend fun postChatbotSessionId(
        sessionId: String,
        input: Any?
    ): ChatbotStepModel? =
        managedExecution {
            mapChatbotStepResponseModelDto(
                chatbotVersionedApiService.postChatbotSessionId(
                    apiVersion,
                    sessionId,
                    ChatbotStepInputModelBodyDto(input)
                )
            )
        }
}
