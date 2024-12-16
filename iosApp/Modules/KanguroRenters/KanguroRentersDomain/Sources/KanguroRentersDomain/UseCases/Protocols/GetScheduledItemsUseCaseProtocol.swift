import Foundation
import KanguroSharedDomain

public protocol GetScheduledItemsUseCaseProtocol {
    func execute(
        policyId: String,
        completion: @escaping ((Result<[ScheduledItem], RequestError>) -> Void)
    )
}
