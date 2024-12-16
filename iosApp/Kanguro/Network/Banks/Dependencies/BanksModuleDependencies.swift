import Foundation
import Resolver
import KanguroSharedDomain
import KanguroSharedData
import KanguroNetworkDomain

class BankModuleDependencies {
    
    // MARK: - Stored Properties
    var bankRepository: BankRepositoryProtocol?
    var getBanksService: GetBanksUseCaseProtocol?
    
    // MARK: - Initializers
    public init(bankRepository: BankRepositoryProtocol? = nil,
                getBanksService: GetBanksUseCaseProtocol? = nil) {
        self.bankRepository = bankRepository
        self.getBanksService = getBanksService
    }
}

// MARK: - ModuleDependencies
extension BankModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {
        let network: NetworkProtocol = Resolver.resolve()
        let bankRepository = self.bankRepository ?? BankRepository(network: network)
        Resolver.register { bankRepository }
        
        let getBanksService = self.getBanksService ?? GetBanks(bankRepo: bankRepository)
        Resolver.register { getBanksService }
    }
}
