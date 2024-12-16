import Foundation
import KanguroSharedDomain

public final class CreatePetCommunications: CreatePetCommunicationsUseCaseProtocol {
    
    private let claimRepo: PetClaimRepositoryProtocol

    public init(claimRepo: PetClaimRepositoryProtocol) {
        self.claimRepo = claimRepo
    }

    public func execute(_ parameters: PetCommunicationParameters, completion:  @escaping ((Result<[Communication], RequestError>) -> Void)) {
        claimRepo.createCommunications(parameters) { result in
            completion(result)
        }
    }
}
