import Foundation
import KanguroSharedDomain

public protocol GetUserRepositoryProtocol {
    
    func getUser(completion: @escaping (Result<User, RequestError>) -> Void)

    func getUser() -> Result<User, RequestError>
}
