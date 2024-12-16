import Foundation
import KanguroSharedDomain

public struct GetRenterPolicy: GetRenterPolicyUseCaseProtocol {

    private let rentersPolicyRepo: RentersPolicyRepositoryProtocol

    public init(rentersPolicyRepo: RentersPolicyRepositoryProtocol) {
        self.rentersPolicyRepo = rentersPolicyRepo
    }

    public func execute(id: String,
                        completion: @escaping ((Result<RenterPolicy, RequestError>) -> Void)) {
        rentersPolicyRepo.getRenterPolicy(id: id) { result in
            completion(result)
        }
    }
}
