import Foundation
import KanguroSharedDomain

public protocol GetFirstChatbotStepUseCaseProtocol {
    func execute(
        _ parameters: UserInputParameters,
        completion: @escaping((Result<ChatbotStep, RequestError>) -> Void)
    )
}
