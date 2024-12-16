import Foundation
import KanguroSharedDomain

public struct GetScheduledItems: GetScheduledItemsUseCaseProtocol {
    
    private let rentersPolicyRepo: RentersPolicyRepositoryProtocol
    
    public init(rentersPolicyRepo: RentersPolicyRepositoryProtocol) {
        self.rentersPolicyRepo = rentersPolicyRepo
    }
    
    public func execute(policyId: String,
                        completion: @escaping ((Result<[ScheduledItem], RequestError>) -> Void)) {
        rentersPolicyRepo.getScheduledItems(policyId: policyId) { result in
            completion(result)
        }
    }
}
