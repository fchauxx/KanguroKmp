import UIKit
import Resolver
import KanguroSharedData
import KanguroNetworkDomain

class ChatbotRepository: ChatbotRepositoryProtocol {

    var service: ChatbotModuleProtocol
    
    init(service: ChatbotModuleProtocol) {
        self.service = service
    }

    func postStartSession(parameters: SessionStartParameters,
                          completion: @escaping((RequestResponse<ChatInteractionStep, NetworkRequestError>) -> Void)) {
        service.postStartSession(parameters: parameters, completion: completion)
    }
    
    func getSession(parameters: GetSessionParameters,
                    completion: @escaping((RequestResponse<[ChatSessionResponse], NetworkRequestError>) -> Void)) {
        service.getSession(parameters: parameters, completion: completion)
    }
    
    func postNextStep(parameters: NextStepParameters,
                      completion: @escaping((RequestResponse<ChatInteractionStep, NetworkRequestError>) -> Void)) {
        service.postNextStep(parameters: parameters, completion: completion)
    }
    
    func getPetInfo(parameters: GetPetIdParameters,
                    completion: @escaping((RequestResponse<ChatPetInfo, NetworkRequestError>) -> Void)) {
        service.getPetInfo(parameters: parameters, completion: completion)
    }
}
