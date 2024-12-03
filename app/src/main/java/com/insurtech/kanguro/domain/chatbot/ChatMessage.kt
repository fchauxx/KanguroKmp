package com.insurtech.kanguro.domain.chatbot

import android.os.Parcelable
import androidx.annotation.Keep
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotMessageFormat
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Keep
@JsonClass(generateAdapter = true)
@Parcelize
data class ChatMessage(
    val content: String? = null,
    val format: ChatbotMessageFormat? = null,
    val order: Int? = null,
    val isFromJavier: Boolean = true // TODO: Change for correct flag
) : Parcelable
