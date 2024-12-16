import Foundation
import KanguroNetworkDomain
import KanguroChatbotDomain
import KanguroSharedDomain

public struct ChatbotSingleChoiceOptionMapper: ModelMapper {
    public typealias T = ChatbotChoice

    public static func map<T>(_ input: some Decodable & Encodable) throws -> T {
        guard let input: RemoteChatbotOption = input as? RemoteChatbotOption else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        guard let id = input.id, let label = input.label else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        let choice = ChatbotChoice(id: id, label: label)
        guard let result: T = choice as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
