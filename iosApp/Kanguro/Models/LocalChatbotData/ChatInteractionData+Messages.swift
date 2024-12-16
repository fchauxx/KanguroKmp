import Foundation

extension ChatInteractionData {
    var messages: [ChatMessage] {
        switch currentId {
        case .SelectPet:
            return [ChatMessage(format: .Text,
                                content: "\("chatbot.newClaim.startHi.message".localized) \(userName)",
                                order: 0,
                                sender: nil),
                    ChatMessage(format: .Text,
                                content: "chatbot.newClaim.startclaim.message".localized,
                                order: 1,
                                sender: nil),
                    ChatMessage(format: .Text,
                                content: "chatbot.newClaim.whoIsClaimFor.message".localized,
                                order: 2,
                                sender: nil)]
        case .PledgeOfHonour:
            return [ChatMessage(format: .Text,
                                content: "chatbot.newClaim.pledgeOfHonor.message".localized,
                                order: 0,
                                sender: nil)]
        case .SelectClaimType:
            return [ChatMessage(format: .Text,
                                content: "chatbot.newClaim.thankYou.message".localized,
                                order: 0,
                                sender: nil),
                    ChatMessage(format: .Text,
                                content: "chatbot.newClaim.selectClaimType.message".localized,
                                order: 1,
                                sender: nil)]
        case .Preventive:
            return [ChatMessage(format: .Text,
                                content: "chatbot.newClaim.preventive.message".localized,
                                order: 0,
                                sender: nil)]
        case .TypeDescription:
            return [ChatMessage(format: .Text,
                                content: "chatbot.newClaim.typeDescription.message".localized,
                                order: 0,
                                sender: nil)]
        case .SelectDate:
            return [ChatMessage(format: .Text,
                                content: "chatbot.newClaim.selectDate.message".localized,
                                order: 0,
                                sender: nil)]
        case .AttachDocuments:
            return [ChatMessage(format: .Text,
                                content: "chatbot.newClaim.receipt.message".localized,
                                order: 0,
                                sender: nil),
                    ChatMessage(format: .Text,
                                content: "chatbot.newClaim.medicalRecords.message".localized,
                                order: 1,
                                sender: nil)]
        case .PetHealthStatePicture:
            return [ChatMessage(format: .Text,
                                content: "chatbot.newClaim.petActualPictureMsg1.message".localized,
                                order: 0,
                                sender: nil),
                    ChatMessage(format: .Text,
                                content: "chatbot.newClaim.petActualPictureMsg2.message".localized,
                                order: 0,
                                sender: nil)]
        case .TypeTotalAmount:
            return [ChatMessage(format: .Text,
                                content: "chatbot.newClaim.claimInvoice.message".localized,
                                order: 0,
                                sender: nil)]
        case .SelectBankAccount:
            return [ChatMessage(format: .Text,
                                content: "chatbot.newClaim.bankAccount.message".localized,
                                order: 0,
                                sender: nil)]
        case .Summary:
            return [ChatMessage(format: .Text,
                                content: "chatbot.newClaim.claimSummary.message".localized,
                                order: 0,
                                sender: nil),
                    ChatMessage(format: .Summary,
                                content: setupSummary(),
                                order: 1,
                                sender: nil)]
        case .Submit:
            return  [ChatMessage(format: .Text, content: "chatbot.newClaim.claimSubmitted.message".localized, order: 0, sender: nil),
                                 ChatMessage(format: .Text,
                                             content: "\("chatbot.newClaim.claimStatusUpdate.message".localized) \(chosenPetName)\("chatbot.newClaim.claimStatusUpdateEnd.message".localized)",
                                             order: 1,
                                             sender: nil),
                                 ChatMessage(format: .Text,
                                             content: "\("chatbot.newClaim.thankYou.message".localized)",
                                             order: 2,
                                             sender: nil)]
        case .PetPolicyNotActive:
            return [ChatMessage(format: .Text,
                                content: "\("chatbot.newClaim.denialSorry.message".localized) \(userName), \("chatbot.newClaim.denialNotActive.beginning.message".localized) \(chosenPetName) \("chatbot.newClaim.denialNotActive.end.message".localized)",
                                order: 0,
                                sender: nil),
                    ChatMessage(format: .Text,
                                content: "chatbot.newClaim.denialContactUs.message".localized,
                                order: 1,
                                sender: nil)]
        case .PetPendingMedicalHistory:
            return [ChatMessage(format: .Text,
                                content: "\("chatbot.newClaim.denialSorry.message".localized) \(userName), \("chatbot.newClaim.denialPendingMedicalHistory.beginning.message".localized) \(chosenPetName) \("chatbot.newClaim.denialPendingMedicalHistory.end.message".localized)",
                                order: 0,
                                sender: nil),
                    ChatMessage(format: .Text,
                                content: "chatbot.newClaim.denialPendingMedicalHistoryCheckReminder.message".localized,
                                order: 1,
                                sender: nil)]
        case .NoAnnualLimit:
            return [ChatMessage(format: .Text,
                                content: "\("chatbot.newClaim.denialSorry.message".localized) \(userName), \("chatbot.newClaim.denialAnnualLimit.message".localized) \(chosenPetName).",
                                order: 0,
                                sender: nil),
                    ChatMessage(format: .Text,
                                content: "chatbot.newClaim.denialContactUs.message".localized,
                                order: 1,
                                sender: nil)]
        case .PetPolicyOnWaitingPeriod:
            return [ChatMessage(format: .Text,
                                content: "\("chatbot.newClaim.denialSorry.message".localized) \(userName), \("chatbot.newClaim.denialWaitingPeriod.end.message".localized) \(chosenPetName).",
                                order: 0,
                                sender: nil),
                    ChatMessage(format: .Text, content: "chatbot.newClaim.denialContactUs.message".localized,
                                order: 1,
                                sender: nil)]
        case .PWNoMoreBenefits:
            return [ChatMessage(format: .Text,
                                content: "\("chatbot.newClaim.denialSorry.message".localized) \(userName), \(chosenPetName) \("chatbot.newClaim.denialNoBenefits.end.message".localized)",
                                order: 0,
                                sender: nil),
                    ChatMessage(format: .Text, content: "chatbot.newClaim.denialContactUs.message".localized,
                                order: 1,
                                sender: nil)]
        case .DateAndAmountDuplicated:
            return  [ChatMessage(format: .Text,
                                 content: "\("chatbot.newClaim.duplicatedClaim.message".localized) \(chosenPetName) \("chatbot.newClaim.duplicatedClaim.end.message".localized)",
                                 order: 0,
                                 sender: nil),
                     ChatMessage(format: .Text,
                                 content: "chatbot.newClaim.duplicatedClaim.message.2".localized,
                                 order: 1,
                                 sender: nil)]
        }
    }
}
