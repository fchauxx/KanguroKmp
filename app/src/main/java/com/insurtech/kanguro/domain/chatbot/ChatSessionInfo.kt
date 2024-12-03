package com.insurtech.kanguro.domain.chatbot

import android.os.Parcelable
import androidx.annotation.Keep
import com.insurtech.kanguro.domain.chatbot.enums.SessionStatus
import com.insurtech.kanguro.domain.chatbot.enums.SessionType
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Keep
@JsonClass(generateAdapter = true)
@Parcelize
data class ChatSessionInfo(
    val sessionId: String? = null,
    val status: SessionStatus? = null,
    val type: SessionType? = null
) : Parcelable
