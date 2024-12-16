import Foundation
import UIKit

enum ChatbotAction: String, Codable, Equatable  {
    
    case Yes
    case No
    case Skip
    case Finish
    case UserCustomInput
    case Signature
    case OtpValidation
    case Reimbursement
    case LocalAction
    case FinishAndRedirect
    case StopClaim
    case StopDuplicatedClaim
    case FinishCommunication
    case FinishFilesUpload
    case UploadFile
    case UploadImage
    case UploadMultipleFiles
    case FinishPetInformation
    case VaccinesAndExamsSelect
    case EditInput
    
    case Accident
    case Illness
    case Other
    case PetSelected
}

struct ChatAction: Codable, Equatable {
    
    var order: Int
    var label: String
    var value: String
    var action: ChatbotAction?
    var isMainAction: Bool?
    var userResponseMessage: String?
}
