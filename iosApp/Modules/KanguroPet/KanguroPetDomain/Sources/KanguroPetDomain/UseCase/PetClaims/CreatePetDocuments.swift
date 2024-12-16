import Foundation
import KanguroSharedDomain

public final class CreatePetDocuments: CreatePetDocumentsUseCaseProtocol {
    
    private let claimRepo: PetClaimRepositoryProtocol

    public init(claimRepo: PetClaimRepositoryProtocol) {
        self.claimRepo = claimRepo
    }

    public func execute(_ parameters: UploadPetAttachmentParameters, completion: @escaping ((Result<[Int], RequestError>) -> Void)) {
        claimRepo.createDocuments(parameters ) { result in
            completion(result)
        }
    }
}
