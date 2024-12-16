import Foundation

public enum ChatbotUserResponseType: Equatable {
    case text
    case date(constraints: ChatbotDateConstraints)
    case singleChoice(options: ChatbotChoiceOptions)
    case video
}
