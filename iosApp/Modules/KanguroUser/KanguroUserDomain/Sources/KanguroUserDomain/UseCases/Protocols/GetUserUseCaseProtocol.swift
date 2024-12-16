import Foundation
import KanguroSharedDomain

public protocol GetUserUseCaseProtocol {
    
    func execute(completion: @escaping (Result<User, RequestError>) -> Void)
}
