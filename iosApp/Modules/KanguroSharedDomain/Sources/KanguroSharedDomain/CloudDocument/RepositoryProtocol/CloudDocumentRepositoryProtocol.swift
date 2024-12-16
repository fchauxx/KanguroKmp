import Foundation

public protocol CloudDocumentRepositoryProtocol {

    func getCloudDocument(
        completion: @escaping ((Result<CloudDocument, RequestError>) -> Void)
    )

    func getCloudDocument(
        by policyId: PolicyParameters,
        completion: @escaping ((Result<CloudDocumentPolicy, RequestError>) -> Void)
    )

    func getClaimDocument(
        by policyAndClaim: ClaimDocumentsParameters,
        completion: @escaping ((Result<ClaimDocument, RequestError>) -> Void)
    )
}
