import Foundation

extension ChatInteractionData {

    var shouldUseOTP: Bool {
        let showOTP = try? getFeatureFlagBoolService.execute(key: .shouldUseOTPValidation)
        if let showOTP, showOTP == true {
            return true
        } else {
            return false
        }
    }

    var actions: [ChatAction] {
        switch currentId {
        case .SelectPet:
            return petOptions
        case .PledgeOfHonour:
            return [ChatAction(order: 1,
                               label: "chatbot.action.sign.label".localized,
                               value: "Signature",
                               action: .Signature,
                               isMainAction: false,
                               userResponseMessage: "🖋")]
        case .SelectClaimType:
            return petClaimCauseActions
        case .Preventive:
            return [ChatAction(order: 1,
                               label: "chatbot.action.seeOptions.label".localized,
                               value: "VaccinesAndExamsSelect",
                               action: .VaccinesAndExamsSelect,
                               isMainAction: false,
                               userResponseMessage: nil)]
        case .SelectBankAccount:
            return [ChatAction(order: 1,
                               label: "chatbot.action.informAccount.label".localized,
                               value: "Reimbursement", action: .Reimbursement,
                               isMainAction: false,
                               userResponseMessage: nil)]
        case .Summary:
            return [ChatAction(order: 1,
                               label: "chatbot.action.submitClaim.label".localized,
                               value: "OtpValidation",
                               action: shouldUseOTP ? .OtpValidation : nil,
                               isMainAction: false,
                               userResponseMessage: "Confirmed")]
        case .Submit:
            return [ChatAction(order: 1,
                               label: "chatbot.action.finish.label".localized,
                               value: "FinishAndRedirect",
                               action: .FinishAndRedirect,
                               isMainAction: false,
                               userResponseMessage: "Finish")]

        case .PetPendingMedicalHistory:
            return [
                ChatAction(order: 1,
                           label: "chatbot.action.finish.label".localized,
                           value: "StopClaim",
                           action: .StopClaim,
                           isMainAction: false,
                           userResponseMessage: "chatbot.action.finish.label".localized),
                ChatAction(order: 2,
                           label: "chatbot.action.continue.label".localized,
                           value: "Continue",
                           action: .Skip,
                           isMainAction: false,
                           userResponseMessage: "chatbot.action.continue.label".localized)
            ]
        case .PetPolicyNotActive, .NoAnnualLimit, .PetPolicyOnWaitingPeriod, .PWNoMoreBenefits:
            return [
                ChatAction(order: 1,
                           label: "chatbot.action.finish.label".localized,
                           value: "StopClaim",
                           action: .StopClaim,
                           isMainAction: false,
                           userResponseMessage: "chatbot.action.finish.label".localized)
            ]
        case .DateAndAmountDuplicated:
            return [
                ChatAction(order: 1,
                           label: "chatbot.action.track.claims.label".localized,
                           value: "StopClaim",
                           action: .StopDuplicatedClaim,
                           isMainAction: false,
                           userResponseMessage: "chatbot.action.track.claims.label".localized)
            ]
        default: return []
        }
    }
}
