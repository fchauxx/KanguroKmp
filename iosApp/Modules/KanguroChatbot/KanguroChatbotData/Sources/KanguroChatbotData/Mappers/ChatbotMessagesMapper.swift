import Foundation
import KanguroNetworkDomain
import KanguroChatbotDomain
import KanguroSharedDomain

public struct ChatbotMessagesMapper: ModelMapper {
    public typealias T = [any KanguroChatbotMessage]

    public static func map<T>(_ input: some Decodable & Encodable) throws -> T {
        guard let input: [RemoteChatbotMessage] = input as? [RemoteChatbotMessage] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let messages: [any KanguroChatbotMessage] = try input.map {
            try ChatbotMessageMapper.map($0)
        }
        guard let result: T = messages as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
