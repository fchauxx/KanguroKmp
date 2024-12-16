import Foundation
import KanguroSharedDomain
import Resolver
import KanguroSharedData
import KanguroNetworkDomain

class VersionModuleDependencies {
    
    // MARK: - Stored Properties
    var backendRepository: BackendVersionRepositoryProtocol?
    var getBackendVersionService: GetBackendVersionUseCaseProtocol?
    
    // MARK: - Initializers
    init(backendRepository: BackendVersionRepository? = nil, getBackendVersionService: GetBackendVersionUseCaseProtocol? = nil) {
        self.backendRepository = backendRepository
        self.getBackendVersionService = getBackendVersionService
    }
}

// MARK: - ModuleDependencies
extension VersionModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {
        let network: NetworkProtocol = Resolver.resolve()
        let backendRepository = self.backendRepository ?? BackendVersionRepository(network: network)
        Resolver.register { backendRepository }

        let getBackendVersionService = self.getBackendVersionService ?? GetBackendVersion(backendRepo: backendRepository)
        Resolver.register { getBackendVersionService }
    }
}
