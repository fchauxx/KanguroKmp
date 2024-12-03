package com.insurtech.kanguro.ui.scenes.javier

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.api.bodies.ChatbotSessionStartBody
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.core.di.RepositoryModule.LocalChatbotRepository
import com.insurtech.kanguro.core.repository.chatbot.IChatbotRepository
import com.insurtech.kanguro.core.repository.pets.IPetsRepository
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.core.utils.SingleLiveEvent
import com.insurtech.kanguro.domain.chatbot.ChatAction
import com.insurtech.kanguro.domain.chatbot.ChatInteractionStep
import com.insurtech.kanguro.domain.chatbot.ChatMessage
import com.insurtech.kanguro.domain.chatbot.enums.*
import com.insurtech.kanguro.domain.chatbot.newchatbot.NewChatBotRepository
import com.insurtech.kanguro.networking.extensions.onError
import com.insurtech.kanguro.networking.extensions.onSuccess
import com.insurtech.kanguro.ui.scenes.chatbot.ChatBotViewModel
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JavierChatBotViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val sessionManager: ISessionManager,
    @LocalChatbotRepository private val chatbotRepository: IChatbotRepository,
    private val petsRepository: IPetsRepository,
    moshi: Moshi
) : ChatBotViewModel(context, chatbotRepository, moshi) {

    val chatFinished = SingleLiveEvent<ChatbotType>()

    var lastChatbotAction: ChatbotType? = null

    private var initialized: Boolean = false

    override fun onChatFinished() {
        lastChatbotAction?.let { chatFinished.postValue(it) }
    }

    fun performChatbotInitialization(chatbotType: ChatbotType) {
        viewModelScope.launch(Dispatchers.IO) {
            petsRepository.getUserPets()
                .onSuccess {
                    (chatbotRepository as? NewChatBotRepository)?.setupChatInteractions(
                        context,
                        sessionManager.sessionInfo?.givenName.orEmpty(),
                        this.body,
                        ""
                    )

                    if (initialized) return@launch
                    initialized = true
                    lastChatbotAction = chatbotType
                    when (chatbotType) {
                        is ChatbotType.NewClaim -> {
                            val sessionInfo = ChatbotSessionStartBody(SessionType.NewClaim)
                            startSession(sessionInfo)
                        }

                        ChatbotType.Generic -> {
                            displayInitialOptions()
                        }

                        is ChatbotType.CompleteClaim -> {
                            // TODO: Placeholder action
                        }

                        is ChatbotType.CompleteMedicalHistory -> {
                            startSession(
                                ChatbotSessionStartBody(
                                    type = SessionType.PetMedicalHistoryDocuments,
                                    petId = chatbotType.petId
                                )
                            )
                        }
                    }
                }.onError {
                    _networkError.postValue(
                        ErrorWithRetry(this) {
                            performChatbotInitialization(chatbotType)
                        }
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

    private fun displayInitialOptions() {
        val initialActions = listOf(
            ChatAction(
                label = context.getString(R.string.file_a_claim),
                action = ChatbotAction.LocalAction,
                value = R.string.file_a_claim.toString()
            ),
            ChatAction(
                label = context.getString(R.string.vet_advice),
                action = ChatbotAction.LocalAction,
                value = R.string.vet_advice.toString()
            ),
            ChatAction(
                label = context.getString(R.string.frequent_questions),
                action = ChatbotAction.LocalAction,
                value = R.string.frequent_questions.toString()
            ),
            ChatAction(
                label = context.getString(R.string.talk_with_support),
                action = ChatbotAction.LocalAction,
                value = R.string.talk_with_support.toString()
            ),
            ChatAction(
                label = context.getString(R.string.im_a_new_pet_parent),
                action = ChatbotAction.LocalAction,
                value = R.string.im_a_new_pet_parent.toString()
            )
        )

        val chatMessages = listOf(
            ChatMessage(
                context.getString(
                    R.string.javier_generic_salutation,
                    sessionManager.sessionInfo?.givenName
                ),
                format = ChatbotMessageFormat.Text
            ),
            ChatMessage(
                context.getString(R.string.javier_generic_what_can_i_do),
                format = ChatbotMessageFormat.Text
            )
        )

        val initialInteractionStep = ChatInteractionStep(
            type = ChatbotInputType.ButtonList,
            actions = initialActions,
            messages = chatMessages,
            orientation = ButtonOrientation.Vertical
        )

        viewModelScope.launch {
            updateChatInteractionStep(initialInteractionStep)
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
