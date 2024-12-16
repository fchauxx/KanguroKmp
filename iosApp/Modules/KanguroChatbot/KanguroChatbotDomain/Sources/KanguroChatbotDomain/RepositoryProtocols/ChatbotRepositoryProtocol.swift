import Foundation
import KanguroSharedDomain

public protocol ChatbotRepositoryProtocol {
    func startSession(
        _ journey: String,
        data: [String: Any?],
        completion: @escaping((Result<String, RequestError>) -> Void)
    )

    func getFirstStep(
        _ sessionId: String,
        completion: @escaping((Result<ChatbotStep, RequestError>) -> Void)
    )

    func sendUserResponse(
        _ sessionId: String,
        _ userResponse: Any,
        completion: @escaping((Result<ChatbotStep, RequestError>) -> Void)
    )
}
