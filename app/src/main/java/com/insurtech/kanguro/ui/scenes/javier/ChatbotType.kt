package com.insurtech.kanguro.ui.scenes.javier

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface ChatbotType : Parcelable {

    @Parcelize
    object Generic : ChatbotType

    @Parcelize
    data class NewClaim(val ongoingSessionId: String?) : ChatbotType

    @Parcelize
    data class CompleteClaim(val ongoingSessionId: String) : ChatbotType

    @Parcelize
    data class CompleteMedicalHistory(val petId: Long) : ChatbotType
}
