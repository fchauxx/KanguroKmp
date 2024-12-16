import Foundation
import Resolver
import KanguroSharedDomain
import KanguroSharedData
import KanguroNetworkDomain

class PasswordModuleDependencies {
    
    // MARK: - Stored Properties
    var passwordRepository: PasswordRepositoryProtocol?
    var updatePasswordService: UpdatePasswordUseCaseProtocol?
    
    // MARK: - Initializers
    public init(passwordRepository: PasswordRepositoryProtocol? = nil,
                updatePasswordService: UpdatePasswordUseCaseProtocol? = nil) {
        self.passwordRepository = passwordRepository
        self.updatePasswordService = updatePasswordService
    }
}

// MARK: - ModuleDependencies
extension PasswordModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {
        let network: NetworkProtocol = Resolver.resolve()
        let passwordRepository = self.passwordRepository ?? PasswordRepository(network: network)
        Resolver.register { passwordRepository }
        
        let updatePasswordService = self.updatePasswordService ?? UpdatePassword(passwordRepo: passwordRepository)
        Resolver.register { updatePasswordService }
    }
}
