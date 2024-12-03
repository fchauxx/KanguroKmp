package com.insurtech.kanguro.ui.scenes.javier

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.core.repository.claims.IClaimsRepository
import com.insurtech.kanguro.core.utils.*
import com.insurtech.kanguro.domain.Base64FileInfo
import com.insurtech.kanguro.domain.chatbot.ChatAction
import com.insurtech.kanguro.domain.chatbot.ChatInteractionStep
import com.insurtech.kanguro.domain.chatbot.ChatMessage
import com.insurtech.kanguro.domain.chatbot.enums.ButtonOrientation
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotAction
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotInputType
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotMessageFormat
import com.insurtech.kanguro.domain.model.CommunicationBody
import com.insurtech.kanguro.networking.dto.CommunicationDto
import com.insurtech.kanguro.networking.extensions.onError
import com.insurtech.kanguro.networking.extensions.onSuccess
import com.insurtech.kanguro.ui.scenes.chatbot.ChatBotMessage
import com.insurtech.kanguro.ui.scenes.chatbot.ImageMessage
import com.insurtech.kanguro.ui.scenes.chatbot.types.SentBy
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class CommunicationChatbotViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    savedStateHandle: SavedStateHandle,
    moshi: Moshi,
    private val claimRepository: IClaimsRepository
) : BaseViewModel() {

    val args = CommunicationChatbotFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val fileConverter = moshi.adapter(Base64FileInfo::class.java)

    private val messagesList = ArrayList<ChatBotMessage>()
    private val _chatbotIsTyping = MutableLiveData<Boolean>()

    val chatbotIsTyping: LiveData<Boolean>
        get() = _chatbotIsTyping
    private val _chatMessages = MutableLiveData<List<ChatBotMessage>>()

    val chatMessages: LiveData<List<ChatBotMessage>>
        get() = _chatMessages
    private val _interactionStep = MutableLiveData<ChatInteractionStep?>()

    val interactionStep: LiveData<ChatInteractionStep?>
        get() = _interactionStep

    val chatFinished = SingleLiveEvent<Unit>()

    private val userFiles: MutableList<String> = arrayListOf()

    fun startChat() {
        viewModelScope.launch(Dispatchers.IO) {
            claimRepository.getClaimCommunications(args.claimId)
                .onSuccess {
                    addMessagesToChat(body)
                    displayInitialOptions()
                }
                .onError {
                    _networkError.postValue(
                        ErrorWithRetry(this) {
                            startChat()
                        }
                    )
                }
        }
    }

    private suspend fun addMessagesToChat(communications: List<CommunicationDto>) {
        _chatbotIsTyping.postValue(true)
        communications.forEachIndexed loop@{ index, communication ->
            if (communication.message == null && communication.fileURL == null) {
                return@loop
            }

            val convertedMsg = ChatBotMessage.fromCommunication(index, communication)
            delay(CHATBOT_RESPONSE_INTERVAL)

            messagesList.add(convertedMsg)
            _chatMessages.postValue(messagesList)
        }
        _chatbotIsTyping.postValue(false)
    }

    private fun displayInitialOptions() {
        val initialActions = listOf(
            ChatAction(
                order = 0,
                label = context.getString(R.string.cancel),
                action = ChatbotAction.StopClaim,
                value = context.getString(R.string.cancel)
            ),
            ChatAction(
                order = 1,
                label = context.getString(R.string.submit),
                action = ChatbotAction.UploadFile,
                value = context.getString(R.string.submit)
            )
        )

        val initialInteractionStep = ChatInteractionStep(
            type = ChatbotInputType.ButtonList,
            actions = initialActions,
            messages = arrayListOf(),
            orientation = ButtonOrientation.Horizontal
        )

        viewModelScope.launch {
            _interactionStep.postValue(initialInteractionStep)
        }
    }

    private fun displayAddMoreStep() {
        val actions = listOf(
            ChatAction(
                order = 0,
                label = context.getString(R.string.add_more),
                action = ChatbotAction.UploadFile,
                value = context.getString(R.string.add_more)
            ),
            ChatAction(
                order = 1,
                label = context.getString(R.string.done),
                action = ChatbotAction.Finish,
                value = context.getString(R.string.done)
            )
        )

        val interactionStep = ChatInteractionStep(
            type = ChatbotInputType.ButtonList,
            actions = actions,
            messages = arrayListOf(),
            orientation = ButtonOrientation.Horizontal
        )

        viewModelScope.launch {
            _interactionStep.postValue(interactionStep)
        }
    }

    fun sendImageReply(image: Bitmap, messageThumbnail: Uri?) {
        val chatMessage = ChatMessage(
            null,
            ChatbotMessageFormat.Text,
            isFromJavier = false
        )
        sendImageReply(image, ImageMessage(chatMessage, messageThumbnail, SentBy.User))
    }

    private fun sendImageReply(image: Bitmap, chatbotMessage: ChatBotMessage) {
        viewModelScope.launch(Dispatchers.IO) {
            val imageResized = BitmapUtils.resizeBitmap(image, 1500) ?: return@launch
            ByteArrayOutputStream().use {
                imageResized.compress(Bitmap.CompressFormat.JPEG, 90, it)
                sendFileReply(it.toByteArray(), chatbotMessage)
            }
        }
    }

    private fun sendFileReply(fileData: ByteArray, chatbotMessage: ChatBotMessage) {
        messagesList.add(chatbotMessage)
        _chatMessages.postValue(messagesList)
        addUserFile(fileData)
        displayAddMoreStep()
    }

    private fun addUserFile(fileData: ByteArray) {
        val imageStr = Base64.encodeToString(fileData, Base64.DEFAULT)
        val convertedImage =
            fileConverter.toJson(Base64FileInfo(imageStr, ".jpg".asValidExtension()))
        userFiles.add(convertedImage)
    }

    fun sendImagesReply(images: List<KanguroFile>) {
        viewModelScope.launch(Dispatchers.IO) {
            images.forEach { image ->
                val bitmap = BitmapFactory.decodeByteArray(
                    image.bytes,
                    0,
                    image.bytes.size
                )

                val chatMessage =
                    ChatMessage(null, ChatbotMessageFormat.Text, isFromJavier = false)

                val imageMessage = ImageMessage(chatMessage, image.uri, SentBy.User)
                messagesList.add(imageMessage)
                _chatMessages.postValue(messagesList)

                val imageResized = BitmapUtils.resizeBitmap(bitmap, 1500) ?: return@launch
                ByteArrayOutputStream().use { byteArrayOutputStream ->
                    imageResized.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream)
                    addUserFile(byteArrayOutputStream.toByteArray())
                }
            }
            displayAddMoreStep()
        }
    }

    fun saveUserFiles() {
        if (userFiles.isEmpty()) {
            return
        }

        val communication = CommunicationBody(
            message = "",
            files = userFiles
        )

        launchLoading(Dispatchers.IO) {
            claimRepository.sendClaimCommunications(args.claimId, communication)
                .onSuccess {
                    chatFinished.postValue(Unit)
                }
                .onError {
                    _networkError.postValue(
                        ErrorWithRetry(this) {
                            saveUserFiles()
                        }
                    )
                }
        }
    }

    companion object {
        private const val CHATBOT_RESPONSE_INTERVAL = 300L
    }
}
