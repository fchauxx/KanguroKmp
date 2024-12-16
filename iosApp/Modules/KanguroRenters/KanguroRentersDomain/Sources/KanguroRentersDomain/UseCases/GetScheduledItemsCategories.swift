import Foundation
import KanguroSharedDomain

public struct GetScheduledItemsCategories: GetScheduledItemsCategoriesUseCaseProtocol {

    private let rentersPolicyRepo: RentersPolicyRepositoryProtocol

    public init(rentersPolicyRepo: RentersPolicyRepositoryProtocol) {
        self.rentersPolicyRepo = rentersPolicyRepo
    }

    public func execute(completion: @escaping ((Result<[ScheduledItemCategory], RequestError>) -> Void)) {
        rentersPolicyRepo.getScheduledItemsCategories { result in
            completion(result)
        }
    }
}
