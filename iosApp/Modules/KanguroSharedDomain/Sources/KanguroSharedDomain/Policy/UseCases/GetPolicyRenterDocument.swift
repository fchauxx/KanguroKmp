import Foundation

public final class GetPolicyRenterDocument: GetPolicyRenterDocumentUseCaseProtocol {
    private let policyRepo: PolicyRepositoryProtocol

    public init(policyRepo: PolicyRepositoryProtocol) {
        self.policyRepo = policyRepo
    }
    
    public func execute(_ parameters: PolicyDocumentParameters, completion: @escaping ((Result<Data, RequestError>) -> Void)) {
        policyRepo.getPolicyRenterDocument(
            parameters
        ) { result in
            completion(result)
        }
    }

}

