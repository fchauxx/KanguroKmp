import Foundation
import KanguroSharedDomain

public protocol GetPetClaimsUseCaseProtocol {
    
    func execute(
        completion: @escaping ((Result<[PetClaim], RequestError>) -> Void)
    )
}
