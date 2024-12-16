import Foundation

public final class GetBanks: GetBanksUseCaseProtocol {
    
    private let bankRepo: BankRepositoryProtocol

    public init(bankRepo: BankRepositoryProtocol) {
        self.bankRepo = bankRepo
    }

    public func execute(completion: @escaping((Result<[BankOption], RequestError>) -> Void)) {
        bankRepo.getBanks { result in
            completion(result)
        }
    }
}
