import Foundation
import KanguroSharedDomain

public final class GetPetVetDirectPaymentPreSignedFileURL: GetPetVetDirectPaymentPreSignedFileURLUseCaseProtocol {

    private let claimRepo: PetClaimRepositoryProtocol

    public init(claimRepo: PetClaimRepositoryProtocol) {
        self.claimRepo = claimRepo
    }

    public func execute(
        _ claimId: String,
        completion: @escaping ((Result<String, RequestError>) -> Void)
    ) {
        claimRepo.getDirectPaymentPreSignedFileURL(claimId: claimId, completion: { result in
            completion(result)
        })
    }
}
