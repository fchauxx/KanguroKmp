import Foundation
import KanguroSharedDomain

public protocol GetPetClaimAttachmentsUseCaseProtocol {
    
    func execute(
        _ parameters: PetClaimParameters,
        completion: @escaping ((Result<[Attachment], RequestError>) -> Void)
    )
}
