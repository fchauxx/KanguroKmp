import Foundation
import KanguroSharedDomain

public protocol DeleteScheduledItemUseCaseProtocol {
    func execute(
        policyId: String,
        scheduledItemId: String,
        completion: @escaping ((Result<Void, RequestError>) -> Void)
    )
}
