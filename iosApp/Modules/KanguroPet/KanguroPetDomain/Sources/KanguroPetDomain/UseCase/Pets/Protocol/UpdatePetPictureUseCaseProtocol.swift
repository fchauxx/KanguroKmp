import Foundation
import KanguroSharedDomain

public protocol UpdatePetPictureUseCaseProtocol {
    func execute(
        parameters: UpdatePetPictureParameters,
        completion: @escaping ((Result<Void,RequestError>) -> Void)
    )
}
