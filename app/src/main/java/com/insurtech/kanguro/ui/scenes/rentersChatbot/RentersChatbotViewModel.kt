package com.insurtech.kanguro.ui.scenes.rentersChatbot

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.core.utils.update
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRentersChatbotRepository
import com.insurtech.kanguro.domain.model.Action
import com.insurtech.kanguro.domain.model.ChatbotMessageModel
import com.insurtech.kanguro.domain.model.ChatbotMessageType
import com.insurtech.kanguro.domain.model.ChatbotStepModel
import com.insurtech.kanguro.domain.model.Journey
import com.insurtech.kanguro.domain.model.Sender
import com.insurtech.kanguro.networking.dto.ErrorDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class RentersChatbotViewModel(
    private val policyId: String,
    private val rentersChatbotRepository: IRentersChatbotRepository,
    private val sessionManager: ISessionManager
) : BaseViewModel() {

    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        rentersChatbotRepository: IRentersChatbotRepository,
        sessionManager: ISessionManager
    ) : this(
        RentersChatbotFragmentArgs.fromSavedStateHandle(savedStateHandle).policyId,
        rentersChatbotRepository,
        sessionManager
    )

    data class ChatbotState(
        val messages: List<ChatbotMessageModel> = emptyList(),
        val action: Action = Action.Unknown,
        var isLoading: Boolean = true,
        var shouldScrollToEnd: Boolean = false
    )

    private val _uiState = MutableStateFlow(ChatbotState())
    val uiState = _uiState.asStateFlow()

    private var sessionId: String? = null

    val userAlreadyChoseCause: Boolean
        get() = sessionManager.sessionInfo?.donation != null

    init {
        startChatbot()
    }

    private fun startChatbot() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val data: Map<String, Any?> = mapOf("policyId" to policyId)
            rentersChatbotRepository.postChatbot(Journey.RentersOnboarding, data)
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            sessionId = result.data
                            postChatbotSession(input = null)
                        }

                        is Result.Error -> {
                            startChatbotError(result)
                        }
                    }
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
        }
    }

    private fun startChatbotError(result: Result.Error) {
        _networkError.postValue(
            ErrorWithRetry(
                NetworkResponse.UnknownError<Unit, ErrorDto>(
                    result.exception,
                    null
                )
            ) {
                startChatbot()
            }
        )
    }

    fun postChatbotSession(input: Any?) {
        val currentSessionId = sessionId

        currentSessionId ?: return

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(isLoading = true, action = Action.Unknown)

            rentersChatbotRepository.postChatbotSessionId(currentSessionId, input)
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            onPostChatbotSessionSuccess(result)
                        }

                        is Result.Error -> {
                            postChatbotSessionError(result, input)
                        }
                    }
                }
        }
    }

    private fun postChatbotSessionError(result: Result.Error, input: Any?) {
        _networkError.postValue(
            ErrorWithRetry(
                NetworkResponse.UnknownError<Unit, ErrorDto>(
                    result.exception,
                    null
                )
            ) {
                postChatbotSession(input = input)
            }
        )
        _uiState.value = _uiState.value.copy(isLoading = false, action = Action.Unknown)
    }

    fun addUserMessage(message: String?) {
        message ?: return

        val messages = _uiState.value.messages.toMutableList()

        messages.add(ChatbotMessageModel(message, Sender.User, ChatbotMessageType.Text))

        _uiState.value = _uiState.value.copy(messages = messages)
    }

    private fun onPostChatbotSessionSuccess(result: Result.Success<ChatbotStepModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                copy(isLoading = true)
            }

            result.data.messages.forEachIndexed { index, message ->
                val messages = _uiState.value.messages.toMutableList()
                messages.add(message)

                if (index != 0) {
                    val delay = min(CHATBOT_RESPONSE_MAX_INTERVAL, message.content.length * 15L)
                    delay(delay)
                }

                _uiState.update {
                    copy(messages = messages, action = Action.Unknown)
                }
            }

            _uiState.update {
                copy(
                    action = result.data.action,
                    isLoading = false,
                    shouldScrollToEnd = true
                )
            }
        }
    }

    fun getPolicyId(): String {
        return policyId
    }

    fun setScrollState(shouldScrollToEnd: Boolean) {
        _uiState.value = _uiState.value.copy(shouldScrollToEnd = shouldScrollToEnd)
    }

    fun onScheduledItemsClosed(isClose: Boolean) {
        if (isClose) {
            postChatbotSession(null)
        }
    }

    fun onVideoUploaded(id: Int) {
        if (id == 0) {
            // id == 0 indicates that the user canceled de process
            postChatbotSession(emptyList<Int>())
        } else {
            postChatbotSession(listOf(id))
            addUserMessage("🎥")
        }
    }

    companion object {
        private const val CHATBOT_RESPONSE_MAX_INTERVAL = 2000L
    }
}
