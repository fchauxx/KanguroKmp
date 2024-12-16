import Foundation
import KanguroSharedDomain

public struct CreateChatbotSession: CreateChatbotSessionUseCaseProtocol {

    private let chatbotRepo: ChatbotRepositoryProtocol

    public init(chatbotRepo: ChatbotRepositoryProtocol) {
        self.chatbotRepo = chatbotRepo
    }

    public func execute(_ parameters: ChatbotJourneyParameters, completion: @escaping ((Result<String, KanguroSharedDomain.RequestError>) -> Void)) {
        chatbotRepo.startSession(parameters.journey.rawValue, data: parameters.data) { result in
            completion(result)
        }
    }
}
