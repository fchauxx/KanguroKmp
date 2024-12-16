import Foundation
import KanguroNetworkDomain
import KanguroChatbotDomain
import KanguroSharedDomain

public struct ChatbotStepMapper: ModelMapper {
    public typealias T = ChatbotStep

    public static func map<T>(_ input: some Decodable & Encodable) throws -> T {
        guard let input: RemoteChatbotPayload = input as? RemoteChatbotPayload else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        guard let messages: [any KanguroChatbotMessage] = try? ChatbotMessagesMapper.map(input.messages) else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let flowAction: ChatbotFlowAction? = try? ChatbotFlowActionMapper.map(input.action)

        let userResponseType: ChatbotUserResponseType? = try? ChatbotUserActionMapper.map(input.action)
        let step = ChatbotStep(
            botMessages: messages,
            expectedUserResponseType: userResponseType,
            expectedFlowAction: flowAction,
            id: input.id ?? ""
        )
        guard let result: T = step as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

