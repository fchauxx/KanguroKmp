import Foundation
import KanguroChatbotDomain
import KanguroDesignSystem

struct ChatInputTypeMapper {
    static func map(_ inputType: ChatMessageInputType) -> DatePickerRange? {
        switch inputType {

        case .anyDate: return .anyDate
        case .choiceButton: return nil
        case .freeText: return nil
        case .futureDate: return .futureDate
        case .pastDate: return .pastDate
        }
    }
}
