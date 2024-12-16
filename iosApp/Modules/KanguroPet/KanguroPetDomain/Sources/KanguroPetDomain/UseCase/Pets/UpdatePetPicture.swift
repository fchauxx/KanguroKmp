import Foundation
import KanguroSharedDomain

public final class UpdatePetPicture: UpdatePetPictureUseCaseProtocol {

    private let petRepo: PetRepositoryProtocol

    public init(petRepo: PetRepositoryProtocol) {
        self.petRepo = petRepo
    }

    public func execute(
        parameters: UpdatePetPictureParameters,
        completion: @escaping ((Result<Void,RequestError>) -> Void)
    ) {
        petRepo.updatePetPicture(parameters) { result in
            completion(result)
        }
    }
}
