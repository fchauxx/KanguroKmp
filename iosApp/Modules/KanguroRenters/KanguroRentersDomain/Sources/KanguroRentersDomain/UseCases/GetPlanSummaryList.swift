import Foundation
import KanguroSharedDomain

public struct GetPlanSummaryList: GetRenterPlanSummaryItemsUseCaseProtocol {
    
    private let rentersPolicyRepo: RentersPolicyRepositoryProtocol
    
    public init(rentersPolicyRepo: RentersPolicyRepositoryProtocol) {
        self.rentersPolicyRepo = rentersPolicyRepo
    }
    
    public func execute(planSummaryEndpointType: PlanSummaryEndpointType,
                        queries: [String : String],
                        completion: @escaping ((Result<[PlanSummaryItemData],
                                                RequestError>) -> Void)) {
        rentersPolicyRepo.getRenterPlanSummaryPricingItems(
            planSummaryEndpointType: planSummaryEndpointType.rawValue,
            queries: queries) { result in
                completion(result)
            }
    }
}
