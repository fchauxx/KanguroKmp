import Foundation
import Resolver
import KanguroSharedDomain
import KanguroSharedData
import KanguroNetworkDomain

class KeycloakModuleDependencies {
    
    // MARK: - Stored Properties
    var keycloakRepository: KeycloakRepositoryProtocol?
    var getKeycloakService: GetKeycloakUseCaseProtocol?

    // MARK: - Initializers
    public init(keycloakRepository: KeycloakRepositoryProtocol? = nil, getKeycloakService: GetKeycloakUseCaseProtocol? = nil) {
        self.keycloakRepository = keycloakRepository
        self.getKeycloakService = getKeycloakService
    }
}

// MARK: - ModuleDependencies
extension KeycloakModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {
        
        let network: NetworkProtocol = Resolver.resolve()
        let keycloakRepository = self.keycloakRepository ?? KeycloakRepository(network: network)
        Resolver.register { keycloakRepository }
        
        let getKeycloakService = self.getKeycloakService ?? GetKeycloak(keycloakRepo: keycloakRepository)
        Resolver.register { getKeycloakService }
    }
}
