import Foundation

public protocol GetClaimDocumentByPolicyAndClaimUseCaseProtocol {
    func execute(
        _ parameters: ClaimDocumentsParameters,
        completion: @escaping ((Result<ClaimDocument, RequestError>) -> Void)
    )
}
