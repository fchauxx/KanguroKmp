import Foundation

public protocol GetFeatureFlagDictionaryUseCaseProtocol {
    func execute(key: KanguroDictionaryFeatureFlagKeys) throws -> Any
}
