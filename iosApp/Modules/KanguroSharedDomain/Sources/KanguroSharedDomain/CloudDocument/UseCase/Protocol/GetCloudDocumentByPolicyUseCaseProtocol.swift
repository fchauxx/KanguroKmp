import Foundation

public protocol GetCloudDocumentsByPolicyUseCaseProtocol {
    func execute(
        _ parameters: PolicyParameters,
        completion: @escaping ((Result<CloudDocumentPolicy, RequestError>) -> Void)
    )
}
