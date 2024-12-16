import Foundation
import KanguroSharedDomain

public protocol GetNextChatbotStepUseCaseProtocol {
    func execute(
        _ parameters: UserInputParameters,
        completion: @escaping((Result<ChatbotStep, RequestError>) -> Void)
    )
}
