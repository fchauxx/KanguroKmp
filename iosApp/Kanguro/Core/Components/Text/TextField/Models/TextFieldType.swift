import Foundation
import UIKit

enum TextFieldType {
    
    // MARK: - Default
    case `default`
    
    // MARK: - Specific types
    case email
    case password
    
    // MARK: - Content type
    case picker
    case calendar
    case calendarChatbot
    case search
    case cellphone
    case bankRoutingNumber
    case bankAccount
    case chatbot
    case currencyChatbot
    case defaultCurrency
    case dataFilter
    case customSearchVet
    case customPicker

    // MARK: - Computed Properties
    var title: String {
        switch self {
        case .email:
            return "textFieldTypeTitle.email".localized
        case .password:
            return "textFieldTypeTitle.password".localized
        default:
            return ""
        }
    }
    var placeholder: String {
        switch self {
        case .email:
            return "textFieldTypePlaceholder.email".localized
        case .password:
            return "textFieldTypePlaceholder.password".localized
        default:
            return ""
        }
    }
    var keyboardType: UIKeyboardType {
        switch self {
        case .email:
            return .emailAddress
        default:
            return .default
        }
    }
    var isChatbot: Bool {
        return (self == .chatbot || self == .calendarChatbot || self == .currencyChatbot)
    }
    var isLogin: Bool {
        return (self == .email || self == .password)
    }
}
