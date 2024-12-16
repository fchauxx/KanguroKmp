import Foundation
import KanguroSharedDomain

public struct GetRenterPolicies: GetRenterPoliciesUseCaseProtocol {
    
    private let rentersPolicyRepo: RentersPolicyRepositoryProtocol
    
    public init(rentersPolicyRepo: RentersPolicyRepositoryProtocol) {
        self.rentersPolicyRepo = rentersPolicyRepo
    }
    
    public func execute(completion: @escaping ((Result<[RenterPolicy], RequestError>) -> Void)) {
        rentersPolicyRepo.getRenterPolicies { result in
            completion(result)
        }
    }
}
