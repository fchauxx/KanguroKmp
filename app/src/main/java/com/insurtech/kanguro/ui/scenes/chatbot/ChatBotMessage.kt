package com.insurtech.kanguro.ui.scenes.chatbot

import android.net.Uri
import com.insurtech.kanguro.common.enums.CommunicationSender
import com.insurtech.kanguro.common.enums.CommunicationType
import com.insurtech.kanguro.domain.chatbot.ChatMessage
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotMessageFormat
import com.insurtech.kanguro.networking.dto.CommunicationDto
import com.insurtech.kanguro.ui.scenes.chatbot.types.SentBy

sealed class ChatBotMessage(
    val message: ChatMessage,
    val sentBy: SentBy,
    val isEditing: Boolean = false,
    val isPending: Boolean = false
) {
    companion object {
        fun fromChatMessage(
            msg: ChatMessage,
            isEditable: Boolean = false,
            isPending: Boolean = false
        ): ChatBotMessage = when (msg.format) {
            ChatbotMessageFormat.Text -> TextMessage(msg, SentBy.Bot, isEditable, isPending)
            ChatbotMessageFormat.Summary -> SummaryMessage(msg)
            ChatbotMessageFormat.Map -> MapMessage(msg)
            else -> TextMessage(msg, SentBy.Bot, isEditable, isPending)
        }

        fun fromCommunication(index: Int, communication: CommunicationDto): ChatBotMessage {
            val content = if (communication.type == CommunicationType.Text) {
                communication.message
            } else {
                "\uD83D\uDCC4" // page facing up emoji
            }

            val chatMessage = ChatMessage(
                content = content,
                format = ChatbotMessageFormat.Text,
                order = index,
                isFromJavier = communication.sender == CommunicationSender.Javier
            )

            val sender = if (communication.sender == CommunicationSender.User) {
                SentBy.User
            } else {
                SentBy.Bot
            }

            return TextMessage(chatMessage, sender, isEditing = false, isPending = false)
        }
    }
}

class TextMessage(message: ChatMessage, sentBy: SentBy, isEditing: Boolean, isPending: Boolean) :
    ChatBotMessage(message, sentBy, isEditing, isPending)

class ImageMessage(
    message: ChatMessage,
    val localImage: Uri?,
    sentBy: SentBy = SentBy.User,
    var isDeletable: Boolean = false,
    val callback: ((Uri) -> Unit)? = null
) : ChatBotMessage(message, sentBy)

class FileMessage(
    message: ChatMessage,
    val uri: Uri?,
    sentBy: SentBy = SentBy.User,
    var isDeletable: Boolean = false,
    val callback: ((Uri) -> Unit)? = null
) : ChatBotMessage(message, sentBy)

class SummaryMessage(message: ChatMessage) : ChatBotMessage(message, SentBy.Bot)

class MapMessage(message: ChatMessage, sentBy: SentBy = SentBy.Bot) :
    ChatBotMessage(message, sentBy)
