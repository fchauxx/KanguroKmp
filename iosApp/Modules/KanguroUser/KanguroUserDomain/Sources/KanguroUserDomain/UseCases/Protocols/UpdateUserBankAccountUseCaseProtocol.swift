import Foundation
import KanguroSharedDomain

public protocol UpdateUserBankAccountUseCaseProtocol {
    
    func execute(_ parameters: BankAccount,
                 completion: @escaping ((Result<Void,RequestError>) -> Void))
}
