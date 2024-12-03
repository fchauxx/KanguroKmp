package com.insurtech.kanguro.domain.chatbot.newchatbot

import com.insurtech.kanguro.domain.chatbot.ChatInteractionStep

data class ChatInteraction(
    val id: ChatInteractionId,
    val step: ChatInteractionStep,
    val nextInteractionId: ChatInteractionId? = null
)

enum class ChatInteractionId {
    SelectPet,
    PledgeOfHonor,
    SelectClaimType,
    SelectedClaimTypePAndW,
    SelectDate,
    TypeDescription,
    TypeTotalAmount,
    AttachDocuments,
    PetHealthStatePicture,
    SelectBankAccount,
    Summary,
    End,

    // Stop Claim Steps
    SelectPetPolicyNotActive,
    SelectPetNoMedicalHistory,
    SelectClaimTypeAAndINoLimit,
    SelectClaimTypeAAndIWaitingPeriod,
    SelectClaimTypePAndWNoMoreBenefits,
    ClaimDuplicated
}
