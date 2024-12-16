import Foundation
import Resolver
import KanguroFeatureFlagDomain
import KanguroFeatureFlagData

class FeatureFlagModuleDependencies {

    // MARK: - Stored Properties
    var repository: KanguroFeatureFlagRepositoryProtocol?
    var getFeatureFlagBool: GetFeatureFlagBoolUseCaseProtocol?
    var getFeatureFlagData: GetFeatureFlagDataUseCaseProtocol?
    var getFeatureFlagDictionary: GetFeatureFlagDictionaryUseCaseProtocol?
    var getFeatureFlagFloat: GetFeatureFlagFloatUseCaseProtocol?
    var getFeatureFlagInt: GetFeatureFlagIntUseCaseProtocol?
    var getFeatureFlagString: GetFeatureFlagStringUseCaseProtocol?
    var setFeatureFlagDefaultValues: SetDefaultValuesUseCaseProtocol?

    // MARK: - Initializers
    init(
        repository: KanguroFeatureFlagRepositoryProtocol? = nil,
        getFeatureFlagBool: GetFeatureFlagBoolUseCaseProtocol? = nil,
        getFeatureFlagData: GetFeatureFlagDataUseCaseProtocol? = nil,
        getFeatureFlagDictionary: GetFeatureFlagDictionaryUseCaseProtocol? = nil,
        getFeatureFlagFloat: GetFeatureFlagFloatUseCaseProtocol? = nil,
        getFeatureFlagInt: GetFeatureFlagIntUseCaseProtocol? = nil,
        getFeatureFlagString: GetFeatureFlagStringUseCaseProtocol? = nil,
        setFeatureFlagDefaultValues: SetDefaultValuesUseCaseProtocol? = nil
    ) {
        self.repository = repository
        self.getFeatureFlagBool = getFeatureFlagBool
        self.getFeatureFlagData = getFeatureFlagData
        self.getFeatureFlagDictionary = getFeatureFlagDictionary
        self.getFeatureFlagFloat = getFeatureFlagFloat
        self.getFeatureFlagInt = getFeatureFlagInt
        self.getFeatureFlagString = getFeatureFlagString
        self.setFeatureFlagDefaultValues = setFeatureFlagDefaultValues
    }
}

// MARK: - ModuleDependencies
extension FeatureFlagModuleDependencies: ModuleDependencies {

    func setupDependencies() {
        let repository: KanguroFeatureFlagRepositoryProtocol = self.repository ?? FirebaseFeatureFlagRepository()
        Resolver.register { repository }
        let getFeatureFlagBool: GetFeatureFlagBoolUseCaseProtocol = self.getFeatureFlagBool ?? GetFeatureFlagBool(repository: repository)
        Resolver.register { getFeatureFlagBool }
        let getFeatureFlagData: GetFeatureFlagDataUseCaseProtocol = self.getFeatureFlagData ?? GetFeatureFlagData(repository: repository)
        Resolver.register { getFeatureFlagData }
        let getFeatureFlagDictionary: GetFeatureFlagDictionaryUseCaseProtocol = self.getFeatureFlagDictionary ?? GetFeatureFlagDictionary(repository: repository)
        Resolver.register { getFeatureFlagDictionary }
        let getFeatureFlagFloat: GetFeatureFlagFloatUseCaseProtocol = self.getFeatureFlagFloat ?? GetFeatureFlagFloat(repository: repository)
        Resolver.register { getFeatureFlagFloat }
        let getFeatureFlagInt: GetFeatureFlagIntUseCaseProtocol = self.getFeatureFlagInt ?? GetFeatureFlagInt(repository: repository)
        Resolver.register { getFeatureFlagInt }
        let getFeatureFlagString: GetFeatureFlagStringUseCaseProtocol = self.getFeatureFlagString ?? GetFeatureFlagString(repository: repository)
        Resolver.register { getFeatureFlagString }
        let setFeatureFlagDefaultValues: SetDefaultValuesUseCaseProtocol = self.setFeatureFlagDefaultValues ??  SetFeatureFlagDefaultValues(repository: repository)
        Resolver.register { setFeatureFlagDefaultValues }
    }
}

