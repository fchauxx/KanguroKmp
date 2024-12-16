import Foundation
import KanguroSharedDomain

public protocol CreatePetVetDirectPaymentClaimUseCaseProtocol {

    func execute(
        _ parameters: PetVetDirectPaymentParameters,
        completion: @escaping ((Result<PetClaim, RequestError>) -> Void)
    )
}
