import Foundation
import KanguroSharedDomain

public protocol CreatePetClaimUseCaseProtocol {
    func execute(
        parameters: NewPetClaimParameters,
        completion: @escaping((Result<PetClaim, RequestError>) -> Void)
    )
}
