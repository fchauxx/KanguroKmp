import Foundation
import KanguroSharedDomain

public protocol PetRepositoryProtocol {
    func getPets(
        completion: @escaping ((Result<[Pet], RequestError>) -> Void)
    )

    func getPet(
        _ parameters: GetPetParameters,
        completion: @escaping ((Result<Pet, RequestError>) -> Void)
    )

    func updatePetPicture(
        _ parameters: UpdatePetPictureParameters,
        completion: @escaping ((Result<Void,RequestError>) -> Void)
    )
}
