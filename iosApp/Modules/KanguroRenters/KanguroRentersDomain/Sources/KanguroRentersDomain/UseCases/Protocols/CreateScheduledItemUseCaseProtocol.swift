import Foundation
import KanguroSharedDomain

public protocol CreateScheduledItemUseCaseProtocol {
    
    func execute(
        _ parameters: ScheduledItemParameters,
        policyId: String,
        completion: @escaping ((Result<ScheduledItem, RequestError>) -> Void)
    )
}
