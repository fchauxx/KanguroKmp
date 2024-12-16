import Foundation
import KanguroSharedDomain

public protocol GetPetClaimAttachmentUseCaseProtocol {
    
    func execute(
        _ parameters: PetClaimAttachmentsParameters,
        completion: @escaping ((Result<Data, RequestError>) -> Void)
    )
}
