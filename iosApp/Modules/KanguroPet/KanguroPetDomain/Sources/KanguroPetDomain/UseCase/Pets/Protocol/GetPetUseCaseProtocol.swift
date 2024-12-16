import Foundation
import KanguroSharedDomain

public protocol GetPetUseCaseProtocol {
    func execute(
        parameters: GetPetParameters,
        completion: @escaping ((Result<Pet, RequestError>) -> Void)
    )
}
