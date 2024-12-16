import Foundation

public protocol GetBanksUseCaseProtocol {
    
    func execute(completion: @escaping((Result<[BankOption], RequestError>) -> Void))
}
