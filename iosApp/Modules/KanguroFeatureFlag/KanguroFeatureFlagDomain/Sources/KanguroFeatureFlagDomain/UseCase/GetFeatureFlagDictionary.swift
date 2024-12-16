import Foundation

public class GetFeatureFlagDictionary: GetFeatureFlagDictionaryUseCaseProtocol {

    let repository: KanguroFeatureFlagRepositoryProtocol

    public init(repository: KanguroFeatureFlagRepositoryProtocol) {
        self.repository = repository
    }

    public func execute(key: KanguroDictionaryFeatureFlagKeys) throws -> Any {
        return try repository.getValue(for: key)
    }
}
