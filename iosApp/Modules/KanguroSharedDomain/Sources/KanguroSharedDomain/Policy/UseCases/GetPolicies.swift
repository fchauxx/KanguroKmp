import Foundation

public final class GetPolicies: GetPoliciesUseCaseProtocol {

    private let policyRepo: PolicyRepositoryProtocol

    public init(policyRepo: PolicyRepositoryProtocol) {
        self.policyRepo = policyRepo
    }

    public func execute(completion: @escaping ((Result<[Policy], RequestError>) -> Void)) {
        policyRepo.getPolicies { result in
            completion(result)
        }
    }
}
