import Foundation

public protocol GetFeatureFlagFloatUseCaseProtocol {
    func execute(key: KanguroFloatFeatureFlagKeys) throws -> Float
}
