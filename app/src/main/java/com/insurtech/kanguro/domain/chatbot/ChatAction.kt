package com.insurtech.kanguro.domain.chatbot

import android.os.Parcelable
import androidx.annotation.Keep
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotAction
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Keep
@JsonClass(generateAdapter = true)
@Parcelize
data class ChatAction(
    val label: String? = null,
    val action: ChatbotAction? = null,
    val isMainAction: Boolean? = false,
    val userResponseMessage: String? = null,
    val order: Int? = null,
    val value: String? = null
) : Parcelable
