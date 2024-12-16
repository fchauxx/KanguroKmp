import Foundation
import KanguroSharedDomain
import Resolver
import KanguroSharedData
import KanguroNetworkDomain

class ExternalLinksModuleDependencies {
    
    // MARK: - Stored Properties
    var externalLinksRepository: ExternalLinksRepositoryProtocol?
    var redirectToPartnerWebsiteService: RedirectPartnerWebpageUseCaseProtocol?
    
    // MARK: - Initializers
    init(externalLinksRepository: ExternalLinksRepositoryProtocol? = nil, redirectToPartnerWebsiteService: RedirectPartnerWebpageUseCaseProtocol? = nil) {
        self.externalLinksRepository = externalLinksRepository
        self.redirectToPartnerWebsiteService = redirectToPartnerWebsiteService
    }
}

// MARK: - ModuleDependencies
extension ExternalLinksModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {
        let network: NetworkProtocol = Resolver.resolve()
        let externalLinksRepository = self.externalLinksRepository ?? ExternalLinksRepository(network: network)
        Resolver.register { externalLinksRepository }

        let redirectToPartnerWebsiteService = self.redirectToPartnerWebsiteService ?? RedirectPartnerWebpage(externalLinksRepo: externalLinksRepository)
        Resolver.register { redirectToPartnerWebsiteService }
    }
}
