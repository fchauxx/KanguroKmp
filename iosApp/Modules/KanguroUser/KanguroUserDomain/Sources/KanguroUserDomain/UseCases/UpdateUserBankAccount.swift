import Foundation
import KanguroSharedDomain

public final class UpdateUserBankAccount: UpdateUserBankAccountUseCaseProtocol {
    
    private let userRepo: UserRepositoryProtocol
    
    public init(userRepo: UserRepositoryProtocol) {
        self.userRepo = userRepo
    }
    
    public func execute(_ parameters: BankAccount,
                        completion: @escaping((Result<Void,RequestError>) -> Void)) {
        userRepo.updateBankAccount(parameters) { result in
            completion(result)
        }
    }
}
