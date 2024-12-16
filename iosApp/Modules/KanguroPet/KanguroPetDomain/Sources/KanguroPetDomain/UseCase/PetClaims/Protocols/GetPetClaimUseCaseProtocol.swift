import Foundation
import KanguroSharedDomain

public protocol GetPetClaimUseCaseProtocol {
    func execute(
        _ parameters: PetClaimParameters,
        completion: @escaping ((Result<PetClaim, RequestError>) -> Void)
    )
}
