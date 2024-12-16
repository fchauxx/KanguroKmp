import Foundation
import KanguroSharedDomain

public final class GetPetClaims: GetPetClaimsUseCaseProtocol {
    
    private let claimRepo: PetClaimRepositoryProtocol

    public init(claimRepo: PetClaimRepositoryProtocol) {
        self.claimRepo = claimRepo
    }

    public func execute(
        completion: @escaping ((Result<[PetClaim], RequestError>) -> Void)
    ) {
        claimRepo.getClaims { result in
            completion(result)
        }
    }
}
