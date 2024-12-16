import Foundation
import KanguroSharedDomain

public struct UpdatePlanSummary: UpdateRenterPlanSummaryItemsUseCaseProtocol {
    
    private let rentersPolicyRepo: RentersPolicyRepositoryProtocol
    
    public init(rentersPolicyRepo: RentersPolicyRepositoryProtocol) {
        self.rentersPolicyRepo = rentersPolicyRepo
    }
    
    public func execute(policyId: String,
                        item: PlanSummary,
                        completion: @escaping ((Result<Void,
                                                RequestError>) -> Void)) {
        rentersPolicyRepo.updateRenterPolicy(item: item, policyId: policyId) { result in
            completion(result)
        }
    }
}
