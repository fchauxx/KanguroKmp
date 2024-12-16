import Foundation

public class SetFeatureFlagDefaultValues: SetDefaultValuesUseCaseProtocol {

    let repository: KanguroFeatureFlagRepositoryProtocol

    public init(repository: KanguroFeatureFlagRepositoryProtocol) {
        self.repository = repository
    }

    public func execute(_ input: [String : NSObject]) throws {
        try repository.setDefaultValues(input)
    }
}
