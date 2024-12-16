import Foundation
import KanguroSharedDomain

public protocol GetPetsUseCaseProtocol {
    func execute(
        completion: @escaping ((Result<[Pet], RequestError>) -> Void)
    )
}
