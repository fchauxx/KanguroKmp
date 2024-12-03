package com.insurtech.kanguro.core.repository.chatbot

import android.util.Base64
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.core.api.KanguroChatbotApiService
import com.insurtech.kanguro.core.api.bodies.ChatbotNextStepBody
import com.insurtech.kanguro.core.api.bodies.ChatbotSessionStartBody
import com.insurtech.kanguro.core.api.responses.PetPolicyResponse
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.core.utils.asValidExtension
import com.insurtech.kanguro.data.repository.BaseRepository
import com.insurtech.kanguro.domain.Base64FileInfo
import com.insurtech.kanguro.domain.chatbot.ChatInteractionStep
import com.insurtech.kanguro.domain.chatbot.ChatSessionInfo
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotAction
import com.insurtech.kanguro.domain.chatbot.enums.SessionStatus
import com.insurtech.kanguro.networking.dto.ErrorDto
import com.squareup.moshi.Moshi
import javax.inject.Inject

class ChatbotRepository @Inject constructor(
    private val kanguroChatbotApiService: KanguroChatbotApiService,
    private val sessionManager: ISessionManager,
    private val moshi: Moshi
) : IChatbotRepository, BaseRepository() {

    private val converter = moshi.adapter(Base64FileInfo::class.java)

    override suspend fun getOngoingSessions(): NetworkResponse<List<ChatSessionInfo>, ErrorDto> {
        return getSafeNetworkResponse {
            val userId = sessionManager.sessionInfo?.id.orEmpty()
            kanguroChatbotApiService.chatbotOngoingSession(userId, SessionStatus.Active)
        }
    }

    override suspend fun startSession(sessionStartInfo: ChatbotSessionStartBody): NetworkResponse<ChatInteractionStep, ErrorDto> {
        return getSafeNetworkResponse {
            kanguroChatbotApiService.chatbotSessionStart(sessionStartInfo)
        }
    }

    override suspend fun nextStep(
        sessionId: String,
        value: String?,
        action: ChatbotAction?
    ): NetworkResponse<ChatInteractionStep, ErrorDto> {
        return getSafeNetworkResponse {
            kanguroChatbotApiService.chatbotSessionNextStep(
                ChatbotNextStepBody(
                    sessionId,
                    value,
                    action.toString()
                )
            )
        }
    }

    override suspend fun nextStep(
        sessionId: String,
        data: ByteArray,
        extension: String,
        action: ChatbotAction?
    ): NetworkResponse<ChatInteractionStep, ErrorDto> {
        val imageStr = Base64.encodeToString(data, Base64.DEFAULT)
        val value = converter.toJson(Base64FileInfo(imageStr, extension.asValidExtension()))
        return nextStep(sessionId, value, action)
    }

    override suspend fun getPetPolicy(sessionId: String): NetworkResponse<PetPolicyResponse, ErrorDto> {
        return getSafeNetworkResponse { kanguroChatbotApiService.getPetPolicy(sessionId) }
    }
}
