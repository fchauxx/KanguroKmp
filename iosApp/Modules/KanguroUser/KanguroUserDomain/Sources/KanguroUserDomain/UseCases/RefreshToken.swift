import Foundation
import KanguroSharedDomain

public final class RefreshToken: RefreshTokenUseCaseProtocol {
    
    private let userRepo: UserRepositoryProtocol
    
    public init(userRepo: UserRepositoryProtocol) {
        self.userRepo = userRepo
    }
    
    public func execute(_ parameters: RefreshTokenParameters,
                        completion: @escaping((Result<Token,RequestError>) -> Void)) {
        userRepo.updateRefreshToken(parameters) { result in
            completion(result)
        }
    }
}
