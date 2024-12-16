import Foundation

public class GetFeatureFlagString: GetFeatureFlagStringUseCaseProtocol {

    let repository: KanguroFeatureFlagRepositoryProtocol

    public init(repository: KanguroFeatureFlagRepositoryProtocol) {
        self.repository = repository
    }

    public func execute(key: KanguroStringFeatureFlagKeys) throws -> String {
        return try repository.getValue(for: key)
    }
}
