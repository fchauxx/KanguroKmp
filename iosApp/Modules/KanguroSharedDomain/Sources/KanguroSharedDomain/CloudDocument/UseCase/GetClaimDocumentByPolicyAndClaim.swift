import Foundation

public final class GetClaimDocumentByPolicyAndClaim: GetClaimDocumentByPolicyAndClaimUseCaseProtocol {

    let cloudDocumentRepo: any CloudDocumentRepositoryProtocol

    public init(cloudDocumentRepo: any CloudDocumentRepositoryProtocol) {
        self.cloudDocumentRepo = cloudDocumentRepo
    }

    public func execute(_ parameters: ClaimDocumentsParameters, completion: @escaping ((Result<ClaimDocument, RequestError>) -> Void)) {
        cloudDocumentRepo.getClaimDocument(by: parameters) { result in
            completion(result)
        }
    }
}
