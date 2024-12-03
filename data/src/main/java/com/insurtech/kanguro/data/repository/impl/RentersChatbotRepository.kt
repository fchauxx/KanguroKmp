package com.insurtech.kanguro.data.repository.impl

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRentersChatbotRepository
import com.insurtech.kanguro.data.source.ChatbotDataSource
import com.insurtech.kanguro.domain.model.ChatbotStepModel
import com.insurtech.kanguro.domain.model.Journey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.NullPointerException
import javax.inject.Inject

// TODO: rename class and interface for a generic name when old chatbot becames removed
class RentersChatbotRepository @Inject constructor(
    private val chatbotRemoteDataSource: ChatbotDataSource
) : IRentersChatbotRepository {

    override suspend fun postChatbot(journey: Journey, data: Map<String, Any?>): Flow<Result<String>> = flow {
        try {
            val sessionId = chatbotRemoteDataSource.postChatbot(journey, data)
            emit(Result.Success(sessionId))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override suspend fun postChatbotSessionId(
        sessionId: String,
        input: Any?
    ): Flow<Result<ChatbotStepModel>> = flow {
        try {
            val chatbotSepModel =
                chatbotRemoteDataSource.postChatbotSessionId(sessionId, input)
            if (chatbotSepModel != null) {
                emit(Result.Success(chatbotSepModel))
            } else {
                emit(Result.Error(NullPointerException()))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}
