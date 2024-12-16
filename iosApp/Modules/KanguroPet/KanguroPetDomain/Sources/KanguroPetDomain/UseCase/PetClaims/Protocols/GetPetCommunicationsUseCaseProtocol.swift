import Foundation
import KanguroSharedDomain

public protocol GetPetCommunicationsUseCaseProtocol {
    func execute(
        _ parameters: PetClaimParameters,
        completion: @escaping ((Result<[Communication], RequestError>) -> Void)
    )
}
