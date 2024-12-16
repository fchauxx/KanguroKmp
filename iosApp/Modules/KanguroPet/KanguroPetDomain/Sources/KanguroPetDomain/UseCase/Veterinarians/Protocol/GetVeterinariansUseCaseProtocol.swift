import Foundation
import KanguroSharedDomain

public protocol GetVeterinariansUseCaseProtocol {
    func execute(
        completion: @escaping ((Result<[Veterinarian], RequestError>) -> Void)
    )
}
