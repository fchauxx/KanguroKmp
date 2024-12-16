import Foundation
import KanguroSharedDomain

public struct UpdateScheduledItemPricing: UpdateScheduledItemPricingUseCaseProtocol {

    private let rentersPolicyRepo: RentersPolicyRepositoryProtocol

    public init(rentersPolicyRepo: RentersPolicyRepositoryProtocol) {
        self.rentersPolicyRepo = rentersPolicyRepo
    }

    public func execute(_ parameters: ScheduledItemParameters,
                        policyId: String,
                        completion: @escaping ((Result<Pricing, RequestError>) -> Void)) {
        rentersPolicyRepo.updateScheduledItemPricing(item: parameters, policyId: policyId) { result in
            completion(result)
        }
    }
}
