package com.insurtech.kanguro.ui.scenes.chatbot

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.api.bodies.ChatbotSessionStartBody
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.core.repository.chatbot.IChatbotRepository
import com.insurtech.kanguro.core.utils.*
import com.insurtech.kanguro.domain.Base64FileInfo
import com.insurtech.kanguro.domain.chatbot.ChatAction
import com.insurtech.kanguro.domain.chatbot.ChatInteractionStep
import com.insurtech.kanguro.domain.chatbot.ChatMessage
import com.insurtech.kanguro.domain.chatbot.enums.ButtonOrientation
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotAction
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotInputType
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotMessageFormat
import com.insurtech.kanguro.domain.model.UserAccount
import com.insurtech.kanguro.networking.extensions.onError
import com.insurtech.kanguro.networking.extensions.onSuccess
import com.insurtech.kanguro.ui.scenes.chatbot.types.SentBy
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

abstract class ChatBotViewModel(
    val context: Context,
    private val chatbotRepository: IChatbotRepository,
    moshi: Moshi
) : BaseViewModel() {

    private val userAccountConverter = moshi.adapter(UserAccount::class.java)

    private val messagesList = ArrayList<ChatBotMessage>()
    private val _chatMessages = MutableLiveData<List<ChatBotMessage>>()
    val chatMessages: LiveData<List<ChatBotMessage>>
        get() = _chatMessages

    private val _interactionStep = MutableLiveData<ChatInteractionStep?>()
    val interactionStep: LiveData<ChatInteractionStep?>
        get() = _interactionStep

    private val _chatbotIsTyping = MutableLiveData<Boolean>()
    val chatbotIsTyping: LiveData<Boolean>
        get() = _chatbotIsTyping

    private var sessionId: String? = null

    var petId: Int? = null
        private set

    var policyId: String? = null
        private set

    private var filesPath: String? = null

    private val attachedFiles = arrayListOf<KanguroFile>()

    private val converter = moshi.adapter(Base64FileInfo::class.java)

    private var lastStep: ChatInteractionStep? = null

    private var lastAction: Pair<ChatbotAction?, String?>? = null

    protected fun startSession(sessionInfo: ChatbotSessionStartBody) {
        _interactionStep.postValue(null)
        _chatbotIsTyping.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            chatbotRepository.startSession(sessionInfo)
                .onSuccess {
                    sessionId = body.sessionId
                    updateChatInteractionStep(body)
                }
                .onError {
                    _networkError.postValue(
                        ErrorWithRetry(this) {
                            startSession(sessionInfo)
                        }
                    )
                }
        }
    }

    protected fun restoreSession(sessionId: String) {
        this.sessionId = sessionId
        getNextStep(null, null)
    }

    open fun sendActionReply(action: ChatAction) {
        addReplyToChat(action)
        getNextStep(action.action, action.value)
    }

    protected fun addReplyToChat(action: ChatAction) {
        val convertedMsg = TextMessage(
            ChatMessage(
                action.userResponseMessage ?: action.label,
                ChatbotMessageFormat.Text,
                isFromJavier = false
            ),
            SentBy.User,
            isEditing = false,
            isPending = false
        )

        messagesList.add(convertedMsg)
        _chatMessages.postValue(messagesList)
    }

    /**
     * Sends an image reply with a visual thumbnail
     */
    fun sendPetPictureReply(
        image: Bitmap,
        messageThumbnail: Uri?,
        extension: String = ".jpg"
    ) {
        val chatMessage = ChatMessage(
            null,
            ChatbotMessageFormat.Text,
            isFromJavier = false
        )

        val chatbotMessage = ImageMessage(chatMessage, messageThumbnail, SentBy.User)

        viewModelScope.launch(Dispatchers.IO) {
            val resizedImage = BitmapUtils.resizeBitmap(image, 1500) ?: return@launch
            ByteArrayOutputStream().use {
                resizedImage.compress(Bitmap.CompressFormat.JPEG, 90, it)

                if (_interactionStep.value!!.type == ChatbotInputType.UploadPetPicture) {
                    sendFileReply(
                        it.toByteArray(),
                        extension,
                        chatbotMessage,
                        ChatbotAction.UploadImage
                    )
                }
            }
        }
    }

    fun sendImagesReply(images: List<KanguroFile>) {
        viewModelScope.launch(Dispatchers.IO) {
            images.forEach { image ->

                val chatMessage =
                    ChatMessage(null, ChatbotMessageFormat.Text, isFromJavier = false)

                val imageMessage = ImageMessage(
                    chatMessage,
                    image.uri,
                    SentBy.User,
                    isDeletable = true,
                    ::onDeleteImagePressed
                )

                messagesList.add(imageMessage)
                _chatMessages.postValue(messagesList)

                val bitmap = BitmapFactory.decodeByteArray(
                    image.bytes,
                    0,
                    image.bytes.size
                )

                val resizedImage = BitmapUtils.resizeBitmap(bitmap, 1500) ?: return@launch
                ByteArrayOutputStream().use { byteArrayOutputStream ->
                    resizedImage.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream)

                    val kanguroFile = KanguroFile(
                        image.uri,
                        byteArrayOutputStream.toByteArray(),
                        image.extension
                    )
                    attachedFiles.add(kanguroFile)
                }
            }
            displayAddMoreOptions()
        }
    }

    fun sendCameraImageReply(image: Bitmap, messageThumbnail: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            val chatMessage = ChatMessage(
                null,
                ChatbotMessageFormat.Text,
                isFromJavier = false
            )

            val imageMessage = ImageMessage(
                chatMessage,
                messageThumbnail,
                SentBy.User,
                isDeletable = true,
                ::onDeleteImagePressed
            )

            messagesList.add(imageMessage)
            _chatMessages.postValue(messagesList)

            val resizedImage = BitmapUtils.resizeBitmap(image, 1500) ?: return@launch
            ByteArrayOutputStream().use { byteArrayOutputStream ->
                resizedImage.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream)

                val kanguroFile = KanguroFile(
                    messageThumbnail!!,
                    byteArrayOutputStream.toByteArray(),
                    "jpg"
                )
                attachedFiles.add(kanguroFile)
            }
            displayAddMoreOptions()
        }
    }

    private fun onDeleteImagePressed(uri: Uri) {
        attachedFiles.removeAll { file ->
            file.uri == uri
        }

        if (attachedFiles.isEmpty()) {
            displayUploadFiles()
        }

        messagesList.removeAll { message ->
            (message as? ImageMessage)?.localImage == uri
        }

        messagesList.removeAll { message ->
            (message as? FileMessage)?.uri == uri
        }

        _chatMessages.postValue(messagesList)
    }

    private fun displayUploadFiles() {
        val addFilesStep = ChatInteractionStep(
            type = ChatbotInputType.UploadPicture
        )

        viewModelScope.launch {
            updateChatInteractionStep(addFilesStep)
        }
    }

    private fun getConvertedFile(file: ByteArray, extension: String): String {
        val fileStr = Base64.encodeToString(file, Base64.DEFAULT)
        return converter.toJson(Base64FileInfo(fileStr, extension.asValidExtension()))
    }

    fun sendReimbursementReply(
        action: ChatbotAction?,
        userAccount: UserAccount
    ) {
        val value = userAccountConverter.toJson(userAccount)
        getNextStep(action, value)
    }

    /**
     * Sends an image reply with a text visual reply (like signature)
     */
    fun sendPledgeOfHonorImageReply(
        image: Bitmap,
        value: String?,
        action: ChatbotAction?
    ) {
        val chatMessage = ChatMessage(
            value,
            ChatbotMessageFormat.Text,
            isFromJavier = false
        )

        val chatbotMessage =
            TextMessage(chatMessage, SentBy.User, isEditing = false, isPending = false)

        viewModelScope.launch(Dispatchers.IO) {
            val resizedImage = BitmapUtils.resizeBitmap(image, 1500) ?: return@launch
            ByteArrayOutputStream().use {
                resizedImage.compress(Bitmap.CompressFormat.PNG, 90, it)
                sendFileReply(it.toByteArray(), ".png", chatbotMessage, action)
            }
        }
    }

    /**
     * Sends a file reply with a text reply (like file attachment)
     */
    fun sendFilesReply(files: List<KanguroFile>) {
        files.forEach { file ->
            val chatMessage = ChatMessage(
                PAGE_FACING_UP_EMOJI,
                ChatbotMessageFormat.Text,
                isFromJavier = false
            )

            val chatBotMessage = FileMessage(
                chatMessage,
                file.uri,
                SentBy.User,
                isDeletable = true,
                ::onDeleteImagePressed
            )
            messagesList.add(chatBotMessage)
            _chatMessages.postValue(messagesList)

            attachedFiles.add(file)
        }
        displayAddMoreOptions()
    }

    private fun sendFileReply(
        fileData: ByteArray,
        extension: String,
        chatbotMessage: ChatBotMessage,
        action: ChatbotAction?
    ) {
        _chatbotIsTyping.postValue(true)
        messagesList.add(chatbotMessage)
        _chatMessages.postValue(messagesList)
        getNextStep(action, fileData, extension)
    }

    private fun displayAddMoreOptions() {
        val addMoreActions = listOf(
            ChatAction(
                order = 0,
                label = context.getString(R.string.add_more),
                action = ChatbotAction.UploadFile,
                value = R.string.add_more.toString()
            ),
            ChatAction(
                order = 1,
                label = context.getString(R.string.done),
                action = ChatbotAction.FinishFilesUpload,
                value = R.string.done.toString()
            )
        )

        val addMoreInteractionStep = ChatInteractionStep(
            type = ChatbotInputType.ButtonList,
            actions = addMoreActions,
            messages = arrayListOf(),
            orientation = ButtonOrientation.Horizontal
        )

        viewModelScope.launch {
            updateChatInteractionStep(addMoreInteractionStep)
        }
    }

    fun sendKanguroFiles() {
        val prevStep = _interactionStep.value
        _interactionStep.postValue(null)

        if (attachedFiles.isEmpty()) {
            displayUploadFiles()
            _interactionStep.postValue(prevStep)
            return
        }

        for (file in attachedFiles) {
            addToPath(getConvertedFile(file.bytes, file.extension))
        }

        redoFilesMessage()

        getNextStep(ChatbotAction.UploadFile, prepareFilesToSend())
    }

    private fun prepareFilesToSend(): String {
        val filesToSend = filesPath.orEmpty()
        filesPath = ""
        attachedFiles.clear()
        return filesToSend
    }

    private fun addToPath(imageStr: String) {
        filesPath = if (filesPath.isNullOrEmpty()) {
            imageStr
        } else {
            "$filesPath|$imageStr"
        }
    }

    private fun redoFilesMessage() {
        val messageCount = messagesList.count()
        val start = messageCount - attachedFiles.count()

        val messagesToRemove = messagesList.subList(start, messageCount)
        val newMessages = arrayListOf<ChatBotMessage>()

        for (message in messagesToRemove) {
            (message as? ImageMessage)?.let {
                newMessages.add(ImageMessage(it.message, it.localImage))
            }
            (message as? FileMessage)?.let {
                newMessages.add(FileMessage(it.message, it.uri))
            }
        }

        messagesList.removeAll(messagesToRemove.toSet())
        messagesList.addAll(newMessages)

        _chatMessages.postValue(messagesList)
    }

    fun setUserInput(value: String) {
        val convertedMsg = TextMessage(
            ChatMessage(
                value,
                ChatbotMessageFormat.Text,
                isFromJavier = false
            ),
            SentBy.User,
            isEditing = false,
            isPending = false
        )

        messagesList.add(convertedMsg)
        _chatMessages.postValue(messagesList)
    }

    fun sendUserCustomInput(value: String, action: ChatbotAction) {
        val lastMessage = messagesList.last()
        if (lastMessage.isEditing) {
            messagesList.removeLast()
            _chatMessages.postValue(messagesList)
        }

        val convertedMsg = TextMessage(
            ChatMessage(
                value,
                ChatbotMessageFormat.Text,
                isFromJavier = false
            ),
            SentBy.User,
            isEditing = false,
            isPending = true
        )

        messagesList.add(convertedMsg)
        _chatMessages.postValue(messagesList)

        displayEditOptions(value, action)
    }

    private fun displayEditOptions(value: String, action: ChatbotAction) {
        val addMoreActions = listOf(
            ChatAction(
                order = 0,
                label = context.getString(R.string.edit),
                action = ChatbotAction.EditLastStep,
                value = context.getString(R.string.edit)
            ),
            ChatAction(
                order = 1,
                label = context.getString(R.string.done),
                action = ChatbotAction.SendLastStep,
                value = context.getString(R.string.done)
            )
        )

        val addMoreInteractionStep = ChatInteractionStep(
            type = ChatbotInputType.ButtonList,
            actions = addMoreActions,
            messages = arrayListOf(),
            orientation = ButtonOrientation.Horizontal
        )

        lastStep = _interactionStep.value
        lastAction = Pair(action, value)

        viewModelScope.launch {
            updateChatInteractionStep(addMoreInteractionStep)
        }
    }

    fun editLastStep() {
        val lastMessage = messagesList.removeLast()
        _chatMessages.postValue(messagesList)

        val convertedMsg = redoLastMessage(lastMessage.message.content, true)

        messagesList.add(convertedMsg)
        _chatMessages.postValue(messagesList)

        _interactionStep.postValue(null)
        _interactionStep.postValue(lastStep)
    }

    fun sendLastStep() {
        messagesList.removeLast()
        _chatMessages.postValue(messagesList)

        val convertedMsg = redoLastMessage(lastAction?.second, false)

        messagesList.add(convertedMsg)
        _chatMessages.postValue(messagesList)

        getNextStep(lastAction?.first, lastAction?.second)
    }

    private fun redoLastMessage(message: String?, isEditing: Boolean): TextMessage {
        return TextMessage(
            ChatMessage(
                message,
                ChatbotMessageFormat.Text,
                isFromJavier = false
            ),
            SentBy.User,
            isEditing,
            false
        )
    }

    fun getNextStep(action: ChatbotAction?, value: String? = "") {
        val sessionId = sessionId.orEmpty()

        val prevStep = _interactionStep.value
        _interactionStep.postValue(null)
        _chatbotIsTyping.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            chatbotRepository.nextStep(sessionId, value, action)
                .onSuccess {
                    updateChatInteractionStep(body)
                }
                .onError {
                    _interactionStep.postValue(prevStep)
                    _chatbotIsTyping.postValue(false)
                    _networkError.postValue(
                        ErrorWithRetry(this) {
                            getNextStep(action, value)
                        }
                    )
                }

            if (policyId == null || petId == null) {
                chatbotRepository.getPetPolicy(sessionId).onSuccess {
                    policyId = this.body.policyId
                    petId = this.body.petId
                }
            }
        }
    }

    fun getNextStep(action: ChatbotAction?, fileData: ByteArray, extension: String) {
        val sessionId = sessionId ?: return
        val prevStep = _interactionStep.value
        _interactionStep.postValue(null)
        viewModelScope.launch(Dispatchers.IO) {
            chatbotRepository.nextStep(sessionId, fileData, extension, action)
                .onSuccess {
                    updateChatInteractionStep(body)
                }
                .onError {
                    _interactionStep.postValue(prevStep)
                    _networkError.postValue(
                        ErrorWithRetry(this) {
                            getNextStep(action, fileData, extension)
                        }
                    )
                }
        }
    }

    protected open suspend fun updateChatInteractionStep(step: ChatInteractionStep) {
        _interactionStep.postValue(null)
        addMessagesToChat(step.messages.orEmpty())
        _interactionStep.postValue(step)
    }

    abstract fun onChatFinished()

    private suspend fun addMessagesToChat(messages: List<ChatMessage>) {
        messages.forEachIndexed { _, chatMessage ->
            val convertedMsg = ChatBotMessage.fromChatMessage(chatMessage)
            delay(CHATBOT_RESPONSE_INTERVAL)
            messagesList.add(convertedMsg)
            _chatMessages.postValue(messagesList)
        }
        _chatbotIsTyping.postValue(false)
    }

    companion object {
        private const val CHATBOT_RESPONSE_INTERVAL = 800L
        private const val PAGE_FACING_UP_EMOJI = "\uD83D\uDCC4"
    }
}
