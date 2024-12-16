import Foundation
import KanguroNetworkDomain
import KanguroChatbotDomain
import KanguroSharedDomain

public struct ChatbotFlowActionMapper: ModelMapper {
    public typealias T = ChatbotFlowAction

    public static func map<T>(_ input: some Decodable & Encodable) throws -> T {
        guard let input: RemoteChatbotAction = input as? RemoteChatbotAction else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        var flowAction: ChatbotFlowAction
        switch input.type?.lowercased() {
        case "scheduleditems":
            var policyId: String = ""
            if let receivedPolicyId: String = input.policyId {
                policyId = receivedPolicyId
            }
            flowAction = .external(flow: .scheduledItem(policyId: policyId))
        case "cameracapturevideo":
            flowAction = .external(flow: .uploadFile)
        case "finish":
            flowAction = .finish
        default:
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        guard let result: T = flowAction as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
