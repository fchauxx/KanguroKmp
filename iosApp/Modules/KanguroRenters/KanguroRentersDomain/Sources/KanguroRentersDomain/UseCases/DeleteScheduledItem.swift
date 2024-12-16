import Foundation
import KanguroSharedDomain

public struct DeleteScheduledItem: DeleteScheduledItemUseCaseProtocol {
    
    private let rentersPolicyRepo: RentersPolicyRepositoryProtocol
    
    public init(rentersPolicyRepo: RentersPolicyRepositoryProtocol) {
        self.rentersPolicyRepo = rentersPolicyRepo
    }
    
    public func execute(policyId: String,
                        scheduledItemId: String,
                        completion: @escaping ((Result<Void, RequestError>) -> Void)) {
        rentersPolicyRepo.deleteScheduledItem(policyId: policyId, scheduledItemId: scheduledItemId) { result in
            completion(result)
        }
    }
}
