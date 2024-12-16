import Foundation
import Combine
import Resolver
import KanguroSharedData
import KanguroStorageDomain
import KanguroNetworkDomain

public class ChatbotModule  {
    
    // MARK: - Dependencies
    @LazyInjected var network: NetworkProtocol
    @LazyInjected var keychain: SecureStorage
}

// MARK: - ChatbotModuleProtocol
extension ChatbotModule: ChatbotModuleProtocol {
    
    func getSession(parameters: GetSessionParameters,
                    completion: @escaping ((RequestResponse<[ChatSessionResponse], NetworkRequestError>) -> Void)) {
        network.request(endpoint: ChatbotModuleEndpoint.getSession,
                        method: .GET,
                        responseType: [ChatSessionResponse].self,
                        errorType: NetworkRequestError.self) { response in
            completion(response)
        }
    }
    
    func postStartSession(parameters: SessionStartParameters,
                          completion: @escaping ((RequestResponse<ChatInteractionStep, NetworkRequestError>) -> Void)) {
        network.request(endpoint: ChatbotModuleEndpoint.sessionStart,
                        method: .POST,
                        parameters: parameters,
                        responseType: ChatInteractionStep.self,
                        errorType: NetworkRequestError.self) { response in
            completion(response)
        }
    }
    
    func postNextStep(parameters: NextStepParameters,
                      completion: @escaping ((RequestResponse<ChatInteractionStep, NetworkRequestError>) -> Void)) {
        network.request(endpoint: ChatbotModuleEndpoint.nextStep,
                        method: .POST,
                        parameters: parameters,
                        responseType: ChatInteractionStep.self,
                        errorType: NetworkRequestError.self) { response in
            completion(response)
        }
    }
    
    func getPetInfo(parameters: GetPetIdParameters,
                    completion: @escaping ((RequestResponse<ChatPetInfo, NetworkRequestError>) -> Void)) {
        network.request(endpoint: ChatbotModuleEndpoint.getPetId(sessionId: parameters.sessionId),
                        method: .GET,
                        responseType: ChatPetInfo.self,
                        errorType: NetworkRequestError.self) { response in
            completion(response)
        }
    }
}
