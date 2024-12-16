import Foundation
import KanguroSharedDomain

public final class GetPetCommunications: GetPetCommunicationsUseCaseProtocol {

    private let claimRepo: PetClaimRepositoryProtocol

    public init(claimRepo: PetClaimRepositoryProtocol) {
        self.claimRepo = claimRepo
    }

    public func execute(_ parameters: PetClaimParameters, completion: @escaping ((Result<[Communication], RequestError>) -> Void)) {
        claimRepo.getCommunications(parameters) { result in
            completion(result)
        }
    }
}
