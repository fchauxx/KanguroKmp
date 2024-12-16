import Foundation
import KanguroSharedDomain

public final class GetPetClaimAttachments: GetPetClaimAttachmentsUseCaseProtocol {

    private let claimRepo: PetClaimRepositoryProtocol

    public init(claimRepo: PetClaimRepositoryProtocol) {
        self.claimRepo = claimRepo
    }

    public func execute(_ parameters: PetClaimParameters,
                        completion: @escaping ((Result<[Attachment], RequestError>) -> Void)) {
        claimRepo.getClaimAttachments(parameters) { result in
            completion(result)
        }
    }
}
