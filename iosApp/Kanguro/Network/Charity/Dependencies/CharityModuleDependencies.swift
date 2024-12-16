import Foundation
import Resolver
import KanguroSharedDomain
import KanguroSharedData
import KanguroNetworkDomain

class CharityModuleDependencies {
    
    // MARK: - Stored Properties
    var charityRepository: CharityRepositoryProtocol?
    var getCharitiesService: GetCharitiesUseCaseProtocol?
    
    // MARK: - Initializers
    init(
        charityRepository: CharityRepositoryProtocol? = nil,
        getCharitiesService: GetCharitiesUseCaseProtocol? = nil
    ) {
        self.charityRepository = charityRepository
        self.getCharitiesService = getCharitiesService
    }
}

// MARK: - ModuleDependencies
extension CharityModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {
        let network: NetworkProtocol = Resolver.resolve()
        let charityRepository = self.charityRepository ?? CharityRepository(network: network)
        Resolver.register { charityRepository }
        let getCharitiesService = self.getCharitiesService ?? GetCharities(charityRepo: charityRepository)
        Resolver.register { getCharitiesService }
    }
}
