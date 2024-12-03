package com.insurtech.kanguro.domain.chatbot

import android.os.Parcelable
import androidx.annotation.Keep
import com.insurtech.kanguro.domain.chatbot.enums.ButtonOrientation
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotInputType
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Keep
@JsonClass(generateAdapter = true)
@Parcelize
data class ChatInteractionStep(
    val type: ChatbotInputType? = null,
    val actions: List<ChatAction>? = null,
    val messages: List<ChatMessage>? = null,
    val orientation: ButtonOrientation? = null,
    val sessionId: String? = null
) : Parcelable
