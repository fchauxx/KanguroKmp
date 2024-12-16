import Foundation

public protocol GetFeatureFlagIntUseCaseProtocol {
    func execute(key: KanguroIntFeatureFlagKeys) throws -> Int
}
