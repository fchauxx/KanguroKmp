import Foundation
import Resolver
import KanguroSharedDomain
import KanguroSharedData
import KanguroNetworkDomain

class TermsOfServiceModuleDependencies {
        
    // MARK: - Stored Properties
    var termsOfServiceRepository: TermsOfServiceRepositoryProtocol?
    var getTermsOfService: GetTermsOfServiceUseCaseProtocol?

    // MARK: - Initializers
    public init(termsOfServiceRepository: TermsOfServiceRepositoryProtocol? = nil, getTermsOfService: GetTermsOfServiceUseCaseProtocol? = nil) {
        self.termsOfServiceRepository = termsOfServiceRepository
        self.getTermsOfService = getTermsOfService
    }
}

// MARK: - ModuleDependencies
extension TermsOfServiceModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {
        
        let network: NetworkProtocol = Resolver.resolve()
        let termsOfServiceRepository = self.termsOfServiceRepository ?? TermsOfServiceRepository(network: network)
        Resolver.register { termsOfServiceRepository }
        
        let getTermsOfService = self.getTermsOfService ?? GetTermsOfService(termsOfServiceRepo: termsOfServiceRepository)
        Resolver.register { getTermsOfService }
    }
}
