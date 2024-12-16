import Foundation
import KanguroSharedDomain

public protocol DeleteUserAccountUseCaseProtocol {
    
    func execute(_ parameters: Bool,
                 completion: @escaping((Result<Void,RequestError>) -> Void))
}
