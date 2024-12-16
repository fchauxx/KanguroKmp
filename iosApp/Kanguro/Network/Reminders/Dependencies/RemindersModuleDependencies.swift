import Foundation
import Resolver
import KanguroSharedDomain
import KanguroSharedData
import KanguroNetworkDomain

class RemindersModuleDependencies {
    
    // MARK: - Stored Properties
    var remindersRepository: RemindersRepositoryProtocol?
    var getRemindersService: GetRemindersUseCaseProtocol?
    
    // MARK: - Initializers
    init(
        remindersRepository: RemindersRepositoryProtocol? = nil,
        getRemindersService: GetRemindersUseCaseProtocol? = nil
    ) {
        self.remindersRepository = remindersRepository
        self.getRemindersService = getRemindersService
    }
}

// MARK: - ModuleDependencies
extension RemindersModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {
        let network: NetworkProtocol = Resolver.resolve()
        let remindersRepository: RemindersRepositoryProtocol = RemindersRepository(network: network)
        Resolver.register { remindersRepository }

        let getRemindersService = self.getRemindersService ?? GetReminders(remindersRepo: remindersRepository)
        Resolver.register { getRemindersService }
    }
}
