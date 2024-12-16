import Foundation
import KanguroNetworkDomain
import KanguroChatbotDomain
import KanguroSharedDomain

public struct ChatbotSingleChoiceOptionsMapper: ModelMapper {
    public typealias T = ChatbotChoiceOptions

    public static func map<T>(_ input: some Decodable & Encodable) throws -> T {
        guard let input: [RemoteChatbotOption] = input as? [RemoteChatbotOption] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let choices: [ChatbotChoice] = try input.map {
            try ChatbotSingleChoiceOptionMapper.map($0)
        }
        guard let result: T = ChatbotChoiceOptions(options: choices) as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
