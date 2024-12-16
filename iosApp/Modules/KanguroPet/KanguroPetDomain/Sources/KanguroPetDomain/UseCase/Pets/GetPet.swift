import Foundation
import KanguroSharedDomain

public final class GetPet: GetPetUseCaseProtocol {

    private let petRepo: PetRepositoryProtocol

    public init(petRepo: PetRepositoryProtocol) {
        self.petRepo = petRepo
    }

    public func execute(
        parameters: GetPetParameters,
        completion: @escaping ((Result<Pet, RequestError>) -> Void)
    ) {
        petRepo.getPet(parameters) { result in
                completion (result)
            }
    }
}
