import Foundation
import KanguroSharedDomain

public protocol UpdateUserUseCaseProtocol {
    func execute(
        _ user: User,
        completion: @escaping ((Result<Void, RequestError>) -> Void)
    )
}
