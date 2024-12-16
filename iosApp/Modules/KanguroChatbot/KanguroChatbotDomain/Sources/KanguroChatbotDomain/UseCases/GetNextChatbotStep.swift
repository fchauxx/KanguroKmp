import Foundation
import KanguroSharedDomain

public struct GetNextChatbotStep: GetNextChatbotStepUseCaseProtocol {

    private let chatbotRepo: ChatbotRepositoryProtocol

    public init(chatbotRepo: ChatbotRepositoryProtocol) {
        self.chatbotRepo = chatbotRepo
    }

    public func execute(_ parameters: UserInputParameters, completion: @escaping ((Result<ChatbotStep, RequestError>) -> Void)) {
        chatbotRepo.sendUserResponse(parameters.chatbotSession, parameters.input) { result in
            completion(result)
        }
    }
}
