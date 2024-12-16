import Foundation
import KanguroSharedDomain

public struct UpdateScheduledItemsImages: UpdateScheduledItemsImagesUseCaseProtocol {

    private let rentersPolicyRepo: RentersPolicyRepositoryProtocol

    public init(rentersPolicyRepo: RentersPolicyRepositoryProtocol) {
        self.rentersPolicyRepo = rentersPolicyRepo
    }

    public func execute(images: [ScheduledItemDefinitiveImageParameter],
                        scheduledItemId: String,
                        completion: @escaping ((Result<Void, RequestError>) -> Void)) {
        rentersPolicyRepo.updateScheduledItemImages(images: images,
                                                    scheduledItemId: scheduledItemId) { result in
            completion(result)
        }
    }
}
