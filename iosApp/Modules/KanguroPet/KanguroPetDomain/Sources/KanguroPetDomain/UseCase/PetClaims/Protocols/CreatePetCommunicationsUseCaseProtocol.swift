import Foundation
import KanguroSharedDomain

public protocol CreatePetCommunicationsUseCaseProtocol {
    
    func execute(
        _ parameters: PetCommunicationParameters,
        completion: @escaping ((Result<[Communication], RequestError>) -> Void)
    )
}
