import Foundation

public class GetFeatureFlagInt: GetFeatureFlagIntUseCaseProtocol {

    let repository: KanguroFeatureFlagRepositoryProtocol

    public init(repository: KanguroFeatureFlagRepositoryProtocol) {
        self.repository = repository
    }

    public func execute(key: KanguroIntFeatureFlagKeys) throws -> Int {
        return try repository.getValue(for: key)
    }
}
