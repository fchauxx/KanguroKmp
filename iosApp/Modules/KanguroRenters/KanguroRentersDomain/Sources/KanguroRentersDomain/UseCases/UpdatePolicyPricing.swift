import Foundation
import KanguroSharedDomain

public struct UpdatePolicyPricing: UpdatePolicyPricingUseCaseProtocol {
    
    private let rentersPolicyRepo: RentersPolicyRepositoryProtocol
    
    public init(rentersPolicyRepo: RentersPolicyRepositoryProtocol) {
        self.rentersPolicyRepo = rentersPolicyRepo
    }
    
    public func execute(policyId: String,
                        item: PlanSummary,
                        completion: @escaping ((Result<Pricing,
                                                RequestError>) -> Void)) {
        rentersPolicyRepo.updateRenterPolicyPricing(item: item,
                                                    policyId: policyId) { result in
            completion(result)
        }
    }
}
