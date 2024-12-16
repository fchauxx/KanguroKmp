import Foundation

public class GetFeatureFlagData: GetFeatureFlagDataUseCaseProtocol {

    let repository: KanguroFeatureFlagRepositoryProtocol

    public init(repository: KanguroFeatureFlagRepositoryProtocol) {
        self.repository = repository
    }

    public func execute(key: KanguroDataFeatureFlagKeys) throws -> Data {
        return try repository.getValue(for: key)
    }
}
