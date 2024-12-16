import Foundation
import KanguroSharedDomain

public final class GetUserBankAccount: GetUserBankAccountUseCaseProtocol {
    
    private let userRepo: UserRepositoryProtocol
    
    public init(userRepo: UserRepositoryProtocol) {
        self.userRepo = userRepo
    }
    
    public func execute(completion: @escaping (Result<BankAccount,RequestError>) -> Void) {
        userRepo.getBankAccount { result in
            completion(result)
        }
    }
}
