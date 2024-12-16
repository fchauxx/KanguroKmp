import Foundation
import KanguroSharedDomain

public protocol GetUserBankAccountUseCaseProtocol {
    
    func execute(completion: @escaping (Result<BankAccount,RequestError>) -> Void)
}
