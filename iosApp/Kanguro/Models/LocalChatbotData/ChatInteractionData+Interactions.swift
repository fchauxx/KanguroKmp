import Foundation

extension ChatInteractionData {

    var nextChatInteraction: ChatInteraction {
        switch currentId {
        case .SelectPet:
            return ChatInteraction(id: .SelectPet,
                                   chatInteractionStep: ChatInteractionStep(type: .ButtonList,
                                                                            orientation: .Vertical,
                                                                            actions: actions,
                                                                            messages: messages,
                                                                            sessionId: ""),
                                   nextInteractionId: .PledgeOfHonour)
        case .PledgeOfHonour:
            return ChatInteraction(id: .PledgeOfHonour,
                                   chatInteractionStep: ChatInteractionStep(type: .ButtonList,
                                                                            orientation: .Horizontal,
                                                                            actions: actions,
                                                                            messages: messages,
                                                                            sessionId: ""),
                                   nextInteractionId: .SelectClaimType)
        case .SelectClaimType:
            return ChatInteraction(id: .SelectClaimType,
                                   chatInteractionStep: ChatInteractionStep(type: .ButtonList,
                                                                            orientation: .Vertical,
                                                                            actions: petClaimCauseActions,
                                                                            messages: messages,
                                                                            sessionId: ""),
                                   nextInteractionId: .TypeDescription)
        case .Preventive:
            return ChatInteraction(id: .Preventive,
                                   chatInteractionStep: ChatInteractionStep(type: .ButtonList,
                                                                            orientation: .Horizontal,
                                                                            actions: actions,
                                                                            messages: messages,
                                                                            sessionId: ""),
                                   nextInteractionId: .SelectDate)
        case .TypeDescription:
            return ChatInteraction(id: .TypeDescription,
                                   chatInteractionStep: ChatInteractionStep(type: .TextInput,
                                                                            orientation: nil,
                                                                            actions: actions,
                                                                            messages: messages,
                                                                            sessionId: ""),
                                   nextInteractionId: .SelectDate)
        case .SelectDate:
            return ChatInteraction(id: .SelectDate,
                                   chatInteractionStep: ChatInteractionStep(type: .DateInput,
                                                                            orientation: nil,
                                                                            actions: actions,
                                                                            messages: messages,
                                                                            sessionId: ""),
                                   nextInteractionId: .AttachDocuments)
        case .AttachDocuments:
            return ChatInteraction(id: .AttachDocuments,
                                   chatInteractionStep: ChatInteractionStep(type: .UploadPicture,
                                                                            orientation: nil,
                                                                            actions: actions,
                                                                            messages: messages,
                                                                            sessionId: ""),
                                   nextInteractionId: .PetHealthStatePicture)
        case .PetHealthStatePicture:
            return ChatInteraction(id: .PetHealthStatePicture,
                                   chatInteractionStep: ChatInteractionStep(type: .UploadPicture,
                                                                            orientation: nil,
                                                                            actions: actions,
                                                                            messages: messages,
                                                                            sessionId: ""),
                                   nextInteractionId: .TypeTotalAmount)
        case .TypeTotalAmount:
            return ChatInteraction(id: .TypeTotalAmount,
                                   chatInteractionStep: ChatInteractionStep(type: .CurrencyInput,
                                                                            orientation: nil,
                                                                            actions: actions,
                                                                            messages: messages,
                                                                            sessionId: ""),
                                   nextInteractionId: .SelectBankAccount)
        case .SelectBankAccount:
            return ChatInteraction(id: .SelectBankAccount,
                                   chatInteractionStep: ChatInteractionStep(type: .BankAccountInput,
                                                                            orientation: nil,
                                                                            actions: actions,
                                                                            messages: messages,
                                                                            sessionId: ""),
                                   nextInteractionId: .Summary)
        case .Summary:
            return ChatInteraction(id: .Summary,
                                   chatInteractionStep: ChatInteractionStep(type: .ButtonList,
                                                                            orientation: .Horizontal,
                                                                            actions: actions,
                                                                            messages: messages,
                                                                            sessionId: ""),
                                   nextInteractionId: .Submit)
        case .Submit:
            return ChatInteraction(id: .Submit,
                                   chatInteractionStep: ChatInteractionStep(type: .Finish,
                                                                            orientation: nil,
                                                                            actions: actions,
                                                                            messages: messages,
                                                                            sessionId: ""),
                                   nextInteractionId: .Submit)
        case .PetPolicyNotActive:
            return ChatInteraction(id: .PetPolicyNotActive,
                                   chatInteractionStep: ChatInteractionStep(type: .ButtonList,
                                                                            orientation: nil,
                                                                            actions: actions,
                                                                            messages: messages,
                                                                            sessionId: ""),
                                   nextInteractionId: .PetPolicyNotActive)
        case .PetPendingMedicalHistory:
            return ChatInteraction(id: .PetPendingMedicalHistory,
                                   chatInteractionStep: ChatInteractionStep(type: .ButtonList,
                                                                            orientation: nil,
                                                                            actions: actions,
                                                                            messages: messages,
                                                                            sessionId: ""),
                                   nextInteractionId: .PledgeOfHonour)
        case .NoAnnualLimit:
            return ChatInteraction(id: .NoAnnualLimit,
                                   chatInteractionStep: ChatInteractionStep(type: .ButtonList,
                                                                            orientation: nil,
                                                                            actions: actions,
                                                                            messages: messages,
                                                                            sessionId: ""),
                                   nextInteractionId: .NoAnnualLimit)
        case .PetPolicyOnWaitingPeriod:
            return ChatInteraction(id: .PetPolicyOnWaitingPeriod,
                                   chatInteractionStep: ChatInteractionStep(type: .ButtonList,
                                                                            orientation: nil,
                                                                            actions: actions,
                                                                            messages: messages,
                                                                            sessionId: ""),
                                   nextInteractionId: .PetPolicyOnWaitingPeriod)
        case .PWNoMoreBenefits:
            return ChatInteraction(id: .PWNoMoreBenefits,
                                   chatInteractionStep: ChatInteractionStep(type: .ButtonList,
                                                                            orientation: nil,
                                                                            actions: actions,
                                                                            messages: messages,
                                                                            sessionId: ""),
                                   nextInteractionId: .PWNoMoreBenefits)
        case .DateAndAmountDuplicated:
            return ChatInteraction(id: .DateAndAmountDuplicated,
                                   chatInteractionStep: ChatInteractionStep(type: .ButtonList,
                                                                            orientation: nil,
                                                                            actions: actions,
                                                                            messages: messages,
                                                                            sessionId: ""),
                                   nextInteractionId: .DateAndAmountDuplicated)
        }
    }

}
