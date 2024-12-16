import Foundation

public class GetFeatureFlagBool: GetFeatureFlagBoolUseCaseProtocol {

    let repository: KanguroFeatureFlagRepositoryProtocol

    public init(repository: KanguroFeatureFlagRepositoryProtocol) {
        self.repository = repository
    }

    public func execute(key: KanguroBoolFeatureFlagKeys) throws -> Bool {
        return try repository.getValue(for: key)
    }
}
