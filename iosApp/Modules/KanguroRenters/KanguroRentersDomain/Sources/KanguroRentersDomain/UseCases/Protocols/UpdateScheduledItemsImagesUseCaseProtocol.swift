import Foundation
import KanguroSharedDomain

public protocol UpdateScheduledItemsImagesUseCaseProtocol {

    func execute(
        images: [ScheduledItemDefinitiveImageParameter],
        scheduledItemId: String,
        completion: @escaping ((Result<Void, RequestError>) -> Void)
    )
}
