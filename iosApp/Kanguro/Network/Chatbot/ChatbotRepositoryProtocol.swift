import Foundation
import KanguroSharedData
import KanguroNetworkDomain

protocol ChatbotRepositoryProtocol {
    
    func postStartSession(parameters: SessionStartParameters,
                          completion: @escaping((RequestResponse<ChatInteractionStep, NetworkRequestError>) -> Void))
    
    func getSession(parameters: GetSessionParameters,
                    completion: @escaping((RequestResponse<[ChatSessionResponse], NetworkRequestError>) -> Void))
    
    func postNextStep(parameters: NextStepParameters,
                      completion: @escaping((RequestResponse<ChatInteractionStep, NetworkRequestError>) -> Void))
    
    func getPetInfo(parameters: GetPetIdParameters,
                    completion: @escaping((RequestResponse<ChatPetInfo, NetworkRequestError>) -> Void))
}
