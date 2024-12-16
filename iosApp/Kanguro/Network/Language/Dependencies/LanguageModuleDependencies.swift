import Foundation
import Resolver
import KanguroSharedDomain
import KanguroSharedData
import KanguroNetworkDomain

class LanguageModuleDependencies {
    
    // MARK: - Stored Properties
    var languageRepository: LanguageRepositoryProtocol?
    var updateLanguageService: UpdateAppLanguageUseCaseProtocol?
    var languageHandler: LanguageHandlerProtocol?

    // MARK: - Initializers
    public init(
        languageRepository: LanguageRepositoryProtocol? = nil,
        updateLanguageService: UpdateAppLanguageUseCaseProtocol? = nil,
        languageHandler: LanguageHandlerProtocol? = nil
    ) {
        self.languageRepository = languageRepository
        self.updateLanguageService = updateLanguageService
        self.languageHandler = languageHandler
    }
}

// MARK: - ModuleDependencies
extension LanguageModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {
        let network: NetworkProtocol = Resolver.resolve()
        let languageRepository = self.languageRepository ?? LanguageRepository(network: network)
        Resolver.register { languageRepository }
        
        let updateLanguageService = self.updateLanguageService ?? UpdateAppLanguage(languageRepo: languageRepository)
        Resolver.register { updateLanguageService }

        let languageHandler = self.languageHandler ?? LanguageHandler()
        Resolver.register { languageHandler }
    }
}
