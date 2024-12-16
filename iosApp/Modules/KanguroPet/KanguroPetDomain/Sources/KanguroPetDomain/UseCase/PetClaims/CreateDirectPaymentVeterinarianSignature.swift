import Foundation
import KanguroSharedDomain

public final class CreateDirectPaymentVeterinarianSignature: CreateDirectPaymentVeterinarianSignatureUseCaseProtocol {

    private let claimRepo: PetClaimRepositoryProtocol

    public init(claimRepo: PetClaimRepositoryProtocol) {
        self.claimRepo = claimRepo
    }

    public func execute(claimId: String, parameters: UploadedDocumentParameters,
                        completion: @escaping ((Result<Void, RequestError>) -> Void)) {
        claimRepo.createDirectPaymentVeterinarianSignature(claimId: claimId,
                                                           parameters: parameters) { result in
            completion(result)
        }
    }
}
