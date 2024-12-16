import Foundation
import KanguroSharedDomain

public protocol UpdateUserRepositoryProtocol {

    func updateUser(_ user: User, completion: @escaping (Result<Void, RequestError>) -> Void)
}
