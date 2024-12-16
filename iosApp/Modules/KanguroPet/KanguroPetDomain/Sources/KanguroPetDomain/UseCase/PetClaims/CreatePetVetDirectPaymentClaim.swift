import Foundation
import KanguroSharedDomain

public final class CreatePetVetDirectPaymentClaim: CreatePetVetDirectPaymentClaimUseCaseProtocol {

    private let claimRepo: PetClaimRepositoryProtocol

    public init(claimRepo: PetClaimRepositoryProtocol) {
        self.claimRepo = claimRepo
    }

    public func execute(_ parameters: PetVetDirectPaymentParameters,
                        completion: @escaping ((Result<PetClaim, RequestError>) -> Void)) {
        claimRepo.createPetVetDirectPaymentClaim(parameters) { result in
            completion(result)
        }
    }
}
