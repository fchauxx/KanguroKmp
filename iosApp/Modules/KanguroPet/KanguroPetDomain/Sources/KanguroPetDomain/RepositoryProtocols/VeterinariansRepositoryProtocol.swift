import Foundation
import KanguroSharedDomain

public protocol VeterinariansRepositoryProtocol {
    func getVeterinarians(
        completion: @escaping ((Result<[Veterinarian], RequestError>) -> Void)
    )
}
