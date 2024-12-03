package com.insurtech.kanguro.ui.scenes.javier

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.core.api.bodies.ChatbotSessionStartBody
import com.insurtech.kanguro.core.repository.chatbot.IChatbotRepository
import com.insurtech.kanguro.core.utils.SingleLiveEvent
import com.insurtech.kanguro.domain.chatbot.ChatAction
import com.insurtech.kanguro.domain.chatbot.enums.*
import com.insurtech.kanguro.domain.chatbot.newchatbot.NewChatBotRepository
import com.insurtech.kanguro.ui.scenes.chatbot.ChatBotViewModel
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicalHistoryChatBotViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val chatbotRepository: IChatbotRepository,
    moshi: Moshi
) : ChatBotViewModel(context, chatbotRepository, moshi) {

    private val chatFinished = SingleLiveEvent<ChatbotType>()

    var lastChatbotAction: ChatbotType? = null

    private var initialized: Boolean = false

    override fun onChatFinished() {
        lastChatbotAction?.let { chatFinished.postValue(it) }
    }

    fun performChatbotInitialization(chatbotType: ChatbotType) {
        viewModelScope.launch(Dispatchers.IO) {
            if (initialized) return@launch
            initialized = true
            lastChatbotAction = chatbotType
            if (chatbotType is ChatbotType.CompleteMedicalHistory) {
                startSession(
                    ChatbotSessionStartBody(
                        type = SessionType.PetMedicalHistoryDocuments,
                        petId = chatbotType.petId
                    )
                )
            }
        }
    }

    @Deprecated("The draft claims list will be removed in the future. So this method wont be needed anymore.")
    private fun restoreOrStartNewClaimSession(sessionId: String?) {
        if (sessionId == null) {
            val sessionInfo = ChatbotSessionStartBody(SessionType.NewClaim)
            startSession(sessionInfo)
        } else {
            restoreSession(sessionId)
        }
    }

    fun startNewFileClaimFlow(action: ChatAction) {
        lastChatbotAction = ChatbotType.NewClaim(null)
        addReplyToChat(action)
        restoreOrStartNewClaimSession(null)
    }

    fun getSubmittedClaimId(): String? {
        return (chatbotRepository as? NewChatBotRepository)?.getClaimId()
    }
}
