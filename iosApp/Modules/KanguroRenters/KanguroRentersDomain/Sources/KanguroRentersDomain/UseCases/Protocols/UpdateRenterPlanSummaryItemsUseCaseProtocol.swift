import Foundation
import KanguroSharedDomain

public protocol UpdateRenterPlanSummaryItemsUseCaseProtocol {
    
    func execute(
        policyId: String,
        item: PlanSummary,
        completion: @escaping ((Result<Void, RequestError>) -> Void)
    )
}
