import Foundation

public protocol GetPolicyDocumentsUseCaseProtocol {
    func execute(
        _ parameters: PolicyParameters,
        completion: @escaping(
            (Result<[PolicyDocumentData], RequestError>) -> Void)
        )
}
