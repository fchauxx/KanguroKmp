import Foundation
import KanguroSharedDomain

public protocol UpdateScheduledItemPricingUseCaseProtocol {
    
    func execute(
        _ parameters: ScheduledItemParameters,
        policyId: String,
        completion: @escaping((Result<Pricing, RequestError>) -> Void)
    )
}
