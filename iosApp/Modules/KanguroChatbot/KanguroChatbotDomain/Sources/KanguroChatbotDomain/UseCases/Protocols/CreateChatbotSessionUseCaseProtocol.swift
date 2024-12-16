import Foundation
import KanguroSharedDomain

public protocol CreateChatbotSessionUseCaseProtocol {
    func execute(
        _ parameters: ChatbotJourneyParameters,
        completion: @escaping((Result<String, RequestError>) -> Void)
    )
}
