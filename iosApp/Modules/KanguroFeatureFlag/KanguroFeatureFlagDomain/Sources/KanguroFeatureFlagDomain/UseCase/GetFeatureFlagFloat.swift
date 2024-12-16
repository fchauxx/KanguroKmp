import Foundation

public class GetFeatureFlagFloat: GetFeatureFlagFloatUseCaseProtocol {

    let repository: KanguroFeatureFlagRepositoryProtocol

    public init(repository: KanguroFeatureFlagRepositoryProtocol) {
        self.repository = repository
    }

    public func execute(key: KanguroFloatFeatureFlagKeys) throws -> Float {
        return try repository.getValue(for: key)
    }
}
