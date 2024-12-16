import Foundation
import KanguroSharedDomain

public protocol UpdatePolicyPricingUseCaseProtocol {
    
    func execute(
        policyId: String,
        item: PlanSummary,
        completion: @escaping ((Result<Pricing, RequestError>) -> Void)
    )
}
