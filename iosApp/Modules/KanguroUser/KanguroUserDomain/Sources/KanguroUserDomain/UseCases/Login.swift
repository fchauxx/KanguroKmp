import Foundation
import KanguroSharedDomain

public final class Login: LoginUseCaseProtocol {
    
    private let userRepo: UserRepositoryProtocol
    
    public init(userRepo: UserRepositoryProtocol) {
        self.userRepo = userRepo
    }
    
    public func execute(_ parameters: UserLoginParameters,
                        completion: @escaping((Result<User,RequestError>) -> Void)) {
        userRepo.login(parameters: parameters) { result in
            completion(result)
        }
    }
}
