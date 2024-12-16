import Foundation
import KanguroSharedDomain

public protocol LoginUseCaseProtocol {
    
    func execute(_ parameters: UserLoginParameters,
                 completion: @escaping((Result<User,RequestError>) -> Void))
}
