import Foundation
import KanguroNetworkDomain
import KanguroChatbotDomain
import KanguroSharedDomain

public struct ChatbotMessageMapper: ModelMapper {
    public typealias T = KanguroChatbotMessage

    public static func map<T>(_ input: some Decodable & Encodable) throws -> T {
        guard let input: RemoteChatbotMessage = input as? RemoteChatbotMessage else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        guard let result: T = input.content as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
