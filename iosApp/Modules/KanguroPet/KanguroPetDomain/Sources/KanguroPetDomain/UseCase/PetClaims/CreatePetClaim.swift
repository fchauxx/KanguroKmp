import Foundation
import KanguroSharedDomain

public final class CreatePetClaim: CreatePetClaimUseCaseProtocol {

    private let claimRepo: PetClaimRepositoryProtocol

    public init(claimRepo: PetClaimRepositoryProtocol) {
        self.claimRepo = claimRepo
    }

    public func execute(parameters: NewPetClaimParameters, completion: @escaping ((Result<PetClaim, RequestError>) -> Void)) {
        claimRepo.createClaim(parameters) { result in
            completion(result)
        }
    }

}
