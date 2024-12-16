import Foundation

public final class GetPolicyDocument: GetPolicyDocumentUseCaseProtocol {

    private let policyRepo: PolicyRepositoryProtocol

    public init(policyRepo: PolicyRepositoryProtocol) {
        self.policyRepo = policyRepo
    }

    public func execute(_ parameters: PolicyDocumentParameters, completion: @escaping ((Result<Data, RequestError>) -> Void)) {
        policyRepo.getPolicyDocument(parameters) { result in
            completion(result)
        }
    }
}
