import Foundation
import KanguroSharedDomain

public protocol RefreshTokenUseCaseProtocol {
    
    func execute(_ parameters: RefreshTokenParameters,
                 completion: @escaping((Result<Token,RequestError>) -> Void))
}
