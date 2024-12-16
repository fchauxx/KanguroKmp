import Foundation

public final class GetPolicy: GetPolicyUseCaseProtocol {
    private let policyRepo: PolicyRepositoryProtocol

    public init(policyRepo: PolicyRepositoryProtocol) {
        self.policyRepo = policyRepo
    }

    public func execute(_ parameters: PolicyParameters, completion: @escaping ((Result<Policy, RequestError>) -> Void)) {
        policyRepo.getPolicy(parameters) { result in
            completion(result)
        }
    }
}
