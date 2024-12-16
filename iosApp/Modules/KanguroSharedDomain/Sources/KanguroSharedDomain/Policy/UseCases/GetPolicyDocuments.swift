import Foundation

public final class GetPolicyDocuments: GetPolicyDocumentsUseCaseProtocol {

    private let policyRepo: PolicyRepositoryProtocol

    public init(policyRepo: PolicyRepositoryProtocol) {
        self.policyRepo = policyRepo
    }

    public func execute(_ parameters: PolicyParameters, completion: @escaping ((Result<[PolicyDocumentData], RequestError>) -> Void)) {
        policyRepo.getPolicyDocuments(
            parameters
        ) { result in
            completion(result)
        }
    }
}
