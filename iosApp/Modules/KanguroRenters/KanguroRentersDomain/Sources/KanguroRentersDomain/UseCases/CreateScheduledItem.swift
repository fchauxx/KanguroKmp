import Foundation
import KanguroSharedDomain

public struct CreateScheduledItem: CreateScheduledItemUseCaseProtocol {

    private let rentersPolicyRepo: RentersPolicyRepositoryProtocol

    public init(rentersPolicyRepo: RentersPolicyRepositoryProtocol) {
        self.rentersPolicyRepo = rentersPolicyRepo
    }

    public func execute(_ scheduledItemParameter: ScheduledItemParameters,
                        policyId: String,
                        completion: @escaping ((Result<ScheduledItem, RequestError>) -> Void)) {
        rentersPolicyRepo.createScheduledItem(item: scheduledItemParameter,
                                              policyId: policyId) { result in
            completion(result)
        }
    }
}
