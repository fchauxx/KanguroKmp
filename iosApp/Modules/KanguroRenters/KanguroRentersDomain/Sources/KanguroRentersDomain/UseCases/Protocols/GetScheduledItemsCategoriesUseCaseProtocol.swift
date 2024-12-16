import Foundation
import KanguroSharedDomain

public protocol GetScheduledItemsCategoriesUseCaseProtocol {

    func execute(
        completion: @escaping ((Result<[ScheduledItemCategory], RequestError>) -> Void)
    )
}
