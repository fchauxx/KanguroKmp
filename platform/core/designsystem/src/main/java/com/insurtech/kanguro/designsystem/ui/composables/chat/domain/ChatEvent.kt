package com.insurtech.kanguro.designsystem.ui.composables.chat.domain

sealed class ChatEvent {
    object OnJavierCardPressed : ChatEvent()
    object OnPhoneCallPressed : ChatEvent()
    object OnEmailPressed : ChatEvent()
    object OnSmsPressed : ChatEvent()
    object OnWhatsappPressed : ChatEvent()
    object OnCommunicationPressed : ChatEvent()
    object OnPetFileClaimPressed : ChatEvent()
    object OnRentersFileClaimPressed : ChatEvent()
    object OnLiveVetPressed : ChatEvent()
    object OnDismissFileAClaimTypeModal : ChatEvent()
    object OnDismissRentersFileAClaimDialog : ChatEvent()
    object OnTryAgainPressed : ChatEvent()
    object PullToRefresh : ChatEvent()
}
