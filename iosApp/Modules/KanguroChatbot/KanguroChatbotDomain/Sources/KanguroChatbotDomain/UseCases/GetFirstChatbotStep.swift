import Foundation
import KanguroSharedDomain

public struct GetFirstChatbotStep: GetFirstChatbotStepUseCaseProtocol {

    private let chatbotRepo: ChatbotRepositoryProtocol

    public init(chatbotRepo: ChatbotRepositoryProtocol) {
        self.chatbotRepo = chatbotRepo
    }

    public func execute(_ parameters: UserInputParameters, completion: @escaping ((Result<ChatbotStep, KanguroSharedDomain.RequestError>) -> Void)) {
        chatbotRepo.getFirstStep(parameters.chatbotSession) { result in
            completion(result)
        }
    }
}
