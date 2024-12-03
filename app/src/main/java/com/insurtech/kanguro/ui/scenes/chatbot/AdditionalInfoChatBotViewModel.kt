package com.insurtech.kanguro.ui.scenes.chatbot

import android.content.Context
import com.insurtech.kanguro.core.api.bodies.ChatbotSessionStartBody
import com.insurtech.kanguro.core.repository.chatbot.IChatbotRepository
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.core.utils.SingleLiveEvent
import com.insurtech.kanguro.domain.chatbot.ChatInteractionStep
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotInputType
import com.insurtech.kanguro.domain.chatbot.enums.SessionType
import com.insurtech.kanguro.domain.model.Pet
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class AdditionalInfoChatBotViewModel @Inject constructor(
    @ApplicationContext context: Context,
    sessionManager: ISessionManager,
    chatbotRepository: IChatbotRepository,
    moshi: Moshi
) : ChatBotViewModel(context, chatbotRepository, moshi) {

    private var currentSession: Pet? = null
    private val remainingSessions = ArrayList<Pet>()
    val chatFinished = SingleLiveEvent<Boolean>()
    val userHasDonated = sessionManager.sessionInfo?.donation != null

    fun restoreAdditionalInfoSessions(pets: Array<Pet>) {
        remainingSessions.addAll(pets)
        startNextSession()
    }

    private fun startNextSession() {
        var index = remainingSessions.indexOfFirst {
            it.additionalInfoSessionId?.isNotEmpty() == true
        }
        if (index == -1) {
            index = remainingSessions.indexOfFirst { it.hasAdditionalInfo == false }
        }
        if (index != -1) {
            remainingSessions.removeAt(index).let {
                currentSession = it
                startOrRestoreSession(it)
            }
        } else {
            currentSession = null
            chatFinished.postValue(true)
        }
    }

    override fun onChatFinished() {
        startNextSession()
    }

    override suspend fun updateChatInteractionStep(step: ChatInteractionStep) {
        super.updateChatInteractionStep(step)
        if (step.type == ChatbotInputType.Finish && step.actions.isNullOrEmpty()) {
            onChatFinished()
        }
    }

    private fun startOrRestoreSession(pet: Pet) {
        if (pet.additionalInfoSessionId != null) {
            restoreSession(pet.additionalInfoSessionId!!)
        } else {
            val sessionInfo = ChatbotSessionStartBody(SessionType.AdditionalInformation, pet.id)
            startSession(sessionInfo)
        }
    }
}
