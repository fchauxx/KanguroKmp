import Foundation
import KanguroSharedDomain

public final class GetPets: GetPetsUseCaseProtocol {

    private let petRepo: PetRepositoryProtocol

    public init(petRepo: PetRepositoryProtocol) {
        self.petRepo = petRepo
    }

    public func execute(
        completion: @escaping ((Result<[Pet], RequestError>) -> Void)
    ) {
        petRepo.getPets { result in
            completion(result)
        }
    }
}
