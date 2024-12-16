import Foundation

public struct ChatbotChoiceOptions: Equatable {
    public var options: [ChatbotChoice]

    public init(options: [ChatbotChoice]) {
        self.options = options
    }
}
