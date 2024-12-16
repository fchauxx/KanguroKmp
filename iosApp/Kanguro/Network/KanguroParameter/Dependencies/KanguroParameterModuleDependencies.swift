import Foundation
import Resolver
import KanguroSharedDomain
import KanguroSharedData
import KanguroNetworkDomain

class KanguroParametersModuleDependencies {
    
    // MARK: - Stored Properties
    var kanguroParameterRepository: KanguroParameterRepositoryProtocol?
    var getInformationTopicsService: GetInformationTopicsUseCaseProtocol?
    
    // MARK: - Initializers
    public init(kanguroParameterRepository: KanguroParameterRepositoryProtocol? = nil,
                getInformationTopicsService: GetInformationTopicsUseCaseProtocol? = nil) {
        self.kanguroParameterRepository = kanguroParameterRepository
        self.getInformationTopicsService = getInformationTopicsService
    }
}

// MARK: - ModuleDependencies
extension KanguroParametersModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {
        let network: NetworkProtocol = Resolver.resolve()
        let kanguroParameterRepository = self.kanguroParameterRepository ?? KanguroParameterRepository(network: network)
        Resolver.register { kanguroParameterRepository }
        
        let getInformationTopicsService = self.getInformationTopicsService ?? GetInformationTopics(kanguroParameterRepo: kanguroParameterRepository)
        Resolver.register { getInformationTopicsService }
    }
}
