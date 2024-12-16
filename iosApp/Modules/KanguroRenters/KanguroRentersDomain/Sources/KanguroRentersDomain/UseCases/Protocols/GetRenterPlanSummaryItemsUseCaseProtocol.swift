import Foundation
import KanguroSharedDomain

public protocol GetRenterPlanSummaryItemsUseCaseProtocol {
    
    func execute(
        planSummaryEndpointType: PlanSummaryEndpointType,
        queries: [String : String],
        completion: @escaping ((Result<[PlanSummaryItemData], RequestError>) -> Void)
    )
}
