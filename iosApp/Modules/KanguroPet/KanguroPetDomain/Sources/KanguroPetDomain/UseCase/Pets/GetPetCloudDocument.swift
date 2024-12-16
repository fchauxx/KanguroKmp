import Foundation
import KanguroSharedDomain

public final class GetPetCloudDocument: GetCloudDocumentUseCaseProtocol {

    public typealias Item = Pet
    let petCloudDocumentRepo: any CloudDocumentRepositoryProtocol

    public init(petCloudDocumentRepo: any CloudDocumentRepositoryProtocol) {
        self.petCloudDocumentRepo = petCloudDocumentRepo
    }

    public func execute(completion: @escaping ((Result<CloudDocument, RequestError>) -> Void)) {
        petCloudDocumentRepo.getCloudDocument { result in            
            completion(result)
        }
    }
}
