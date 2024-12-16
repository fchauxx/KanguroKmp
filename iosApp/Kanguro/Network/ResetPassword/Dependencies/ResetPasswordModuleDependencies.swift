import Foundation
import Resolver
import KanguroSharedDomain
import KanguroSharedData
import KanguroNetworkDomain

class ResetPasswordModuleDependencies {
    
    // MARK: - Stored Properties
    var resetPasswordRepository: ResetPasswordRepositoryProtocol?
    var createResetPasswordService: CreateResetPasswordUseCaseProtocol?
    
    // MARK: - Initializers
    public init(resetPasswordRepository: ResetPasswordRepositoryProtocol? = nil,
                createResetPasswordService: CreateResetPasswordUseCaseProtocol? = nil) {
        self.resetPasswordRepository = resetPasswordRepository
        self.createResetPasswordService = createResetPasswordService
    }
}

// MARK: - ModuleDependencies
extension ResetPasswordModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {
        let network: NetworkProtocol = Resolver.resolve()
        let resetPasswordRepository = self.resetPasswordRepository ?? ResetPasswordRepository(network: network)
        Resolver.register { resetPasswordRepository }
        
        let createResetPasswordService = self.createResetPasswordService ?? CreateResetPassword(resetPasswordRepo: resetPasswordRepository)
        Resolver.register { createResetPasswordService }
    }
}
