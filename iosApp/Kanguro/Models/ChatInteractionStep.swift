import Foundation

enum ChatbotInteractionType: String, Codable {
    
    case ButtonList
    case UploadPicture
    case TextInput
    case NumberInput
    case Signature
    case Finish
    case DateInput
    case CurrencyInput
    case OtpValidation
    case UploadMultipleFiles
    case BankAccountInput
    case UploadPetPicture
}

enum ButtonOrientation: String, Codable {
    
    case Horizontal
    case Vertical
}

enum SessionType: String, Codable {
    
    case AdditionalInformation
    case NewClaim
    case Central
    case PetMedicalHistoryDocuments
    case Communication
}

struct ChatInteractionStep: Codable {
    
    var type: ChatbotInteractionType
    var orientation: ButtonOrientation?
    var actions: [ChatAction]
    var messages: [ChatMessage]
    var sessionId: String
}

struct ChatInteraction {
    let id: ChatInteractionId
    let chatInteractionStep: ChatInteractionStep
    let nextInteractionId: ChatInteractionId
}

enum ChatInteractionId {
    case SelectPet
    case PledgeOfHonour
    case SelectClaimType
    case Preventive
    case SelectDate
    case TypeDescription
    case TypeTotalAmount
    case AttachDocuments
    case PetHealthStatePicture
    case SelectBankAccount
    case Summary
    case Submit
    
    case PetPolicyNotActive
    case PetPendingMedicalHistory
    case NoAnnualLimit
    case PetPolicyOnWaitingPeriod
    case PWNoMoreBenefits
    case DateAndAmountDuplicated
}
