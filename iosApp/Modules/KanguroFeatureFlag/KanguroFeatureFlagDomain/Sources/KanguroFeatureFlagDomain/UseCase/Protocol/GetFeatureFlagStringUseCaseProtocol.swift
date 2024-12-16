import Foundation

public protocol GetFeatureFlagStringUseCaseProtocol {
    func execute(key: KanguroStringFeatureFlagKeys) throws -> String
}
