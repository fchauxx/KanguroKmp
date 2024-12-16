import Foundation

public struct ChatbotStep: Equatable {
    public var botMessages: [any KanguroChatbotMessage]
    public var expectedUserResponseType: ChatbotUserResponseType?
    public var expectedFlowAction: ChatbotFlowAction?
    public var id: String

    public init(botMessages: [any KanguroChatbotMessage], expectedUserResponseType: ChatbotUserResponseType? = nil, expectedFlowAction: ChatbotFlowAction? = nil, id: String) {
        self.botMessages = botMessages
        self.expectedUserResponseType = expectedUserResponseType
        self.expectedFlowAction = expectedFlowAction
        self.id = id
    }

    public static func == (lhs: ChatbotStep, rhs: ChatbotStep) -> Bool {
        guard lhs.botMessages.count == rhs.botMessages.count else {
            return false
        }
        guard lhs.expectedUserResponseType == rhs.expectedUserResponseType && lhs.expectedFlowAction == rhs.expectedFlowAction && rhs.id == lhs.id else {
            return false
        }

        for msg in lhs.botMessages {
            let containsMsg = rhs.botMessages.contains { rhsMsg in
                rhsMsg.isEqualTo(msg)
            }
            guard containsMsg else {
                return false
            }
        }
        return true
    }
}
