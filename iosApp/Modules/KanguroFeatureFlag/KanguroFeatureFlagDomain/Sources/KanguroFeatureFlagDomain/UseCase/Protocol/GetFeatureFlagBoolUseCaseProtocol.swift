import Foundation

public protocol GetFeatureFlagBoolUseCaseProtocol {
    func execute(key: KanguroBoolFeatureFlagKeys) throws -> Bool
}
