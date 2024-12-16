import Foundation
import KanguroNetworkDomain
import KanguroChatbotDomain
import KanguroSharedDomain

public struct ChatbotUserActionMapper: ModelMapper {
    public typealias T = ChatbotUserResponseType

    public static func map<T>(_ input: some Decodable & Encodable) throws -> T {
        guard let input: RemoteChatbotAction = input as? RemoteChatbotAction else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        var userAction: ChatbotUserResponseType
        switch input.type?.lowercased() {
        case "text": userAction = .text
        case "date":
            guard let dateConstraints: ChatbotDateConstraints = try? ChatbotDateTypeMapper.map(input.dateRange) else {
                throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
            }
            userAction = .date(constraints: dateConstraints)
        case "singlechoice":
            guard let options: ChatbotChoiceOptions = try? ChatbotSingleChoiceOptionsMapper.map(input.options) else {
                throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
            }
            userAction = .singleChoice(options: options)
        case "cameracapturevideo": userAction = .video
        default:
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        guard let result: T = userAction as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
