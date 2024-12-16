import Foundation
import KanguroSharedDomain

public final class GetPetClaim: GetPetClaimUseCaseProtocol {
    
    private let claimRepo: PetClaimRepositoryProtocol
    
    public init(claimRepo: PetClaimRepositoryProtocol) {
        self.claimRepo = claimRepo
    }
    
    public func execute(
        _ parameters: PetClaimParameters,
        completion: @escaping ((Result<PetClaim, RequestError>) -> Void)
    ) {
        claimRepo.getClaim(parameters) { result in
            completion(result)
        }
    }
}
