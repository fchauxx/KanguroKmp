import Foundation
import KanguroSharedDomain

public final class DeleteUserAccount: DeleteUserAccountUseCaseProtocol {
    
    private let userRepo: UserRepositoryProtocol
    
    public init(userRepo: UserRepositoryProtocol) {
        self.userRepo = userRepo
    }
    
    public func execute(_ parameters: Bool,
                 completion: @escaping((Result<Void,RequestError>) -> Void)) {
        userRepo.createAccountDeletionOrder(parameters) { result in
            completion(result)
        }
    }
}
