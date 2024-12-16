import Foundation
import KanguroSharedDomain

public final class GetPetClaimAttachment: GetPetClaimAttachmentUseCaseProtocol {

    private let claimRepo: PetClaimRepositoryProtocol

    public init(claimRepo: PetClaimRepositoryProtocol) {
        self.claimRepo = claimRepo
    }

    public func execute(_ parameters: PetClaimAttachmentsParameters, completion: @escaping ((Result<Data, RequestError>) -> Void)) {
        claimRepo.getClaimAttachment(parameters) { result in
            completion(result)
        }
    }
}
