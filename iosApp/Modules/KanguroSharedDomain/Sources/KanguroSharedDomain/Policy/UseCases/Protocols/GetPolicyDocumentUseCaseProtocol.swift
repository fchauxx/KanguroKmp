import Foundation

public protocol GetPolicyDocumentUseCaseProtocol {
    func execute(
        _ parameters: PolicyDocumentParameters,
        completion: @escaping(
            (Result<Data, RequestError>) -> Void)
        )
}
