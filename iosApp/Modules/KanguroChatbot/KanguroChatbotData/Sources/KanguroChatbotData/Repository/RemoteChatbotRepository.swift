import Foundation
import KanguroChatbotDomain
import KanguroNetworkDomain
import KanguroSharedDomain

public class RemoteChatbotRepository: ChatbotRepositoryProtocol {

    private let network: NetworkProtocol

    public init(network: NetworkProtocol) {
        self.network = network
    }

    public func startSession(
        _ journey: String,
        data: [String: Any?],
        completion: @escaping((Result<String, RequestError>) -> Void)
    ) {
        let codableData: [String: AnyCodable?] = data.mapValues { value in
            guard let value = value as? Codable else {
                return nil
            }
            return AnyCodable(value)
        }
        
        network.request(
            endpoint: ChatbotModuleEndpoint.session,
            method: .POST,
            parameters: RemoteChatbotJourneyParameters(journey: journey, data: codableData),
            responseType: RemoteSessionId.self,
            errorType: NetworkRequestError.self
        ) { response in
            ResponseHandler.handle(mapper: ChatbotSessionMapper(), response: response, completion: completion)
        }
    }

    public func getFirstStep(
        _ sessionId: String,
        completion: @escaping((Result<ChatbotStep, RequestError>) -> Void)
    ) {
        network.request(
            endpoint: ChatbotModuleEndpoint.step(sessionId: sessionId),
            method: .POST,
            parameters: RemoteChatbotUserResponseParameters(""),
            responseType: RemoteChatbotPayload.self,
            errorType: NetworkRequestError.self
        ) { response in
            ResponseHandler.handle(mapper: ChatbotStepMapper(), response: response, completion: completion)
        }
    }

    public func sendUserResponse(
        _ sessionId: String,
        _ userResponse: Any,
        completion: @escaping((Result<ChatbotStep, RequestError>) -> Void)
    ) {
        let parameters = RemoteChatbotUserResponseParameters(userResponse)
        
        network.request(
            endpoint: ChatbotModuleEndpoint.step(sessionId: sessionId),
            method: .POST,
            parameters: parameters,
            responseType: RemoteChatbotPayload.self,
            errorType: NetworkRequestError.self
        ) { response in
            ResponseHandler.handle(mapper: ChatbotStepMapper(), response: response, completion: completion)
        }
    }
}
