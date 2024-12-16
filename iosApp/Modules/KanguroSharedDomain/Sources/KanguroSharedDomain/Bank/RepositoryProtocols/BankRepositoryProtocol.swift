import Foundation

public protocol BankRepositoryProtocol {
    
    func getBanks(completion: @escaping((Result<[BankOption], RequestError>) -> Void))
}
