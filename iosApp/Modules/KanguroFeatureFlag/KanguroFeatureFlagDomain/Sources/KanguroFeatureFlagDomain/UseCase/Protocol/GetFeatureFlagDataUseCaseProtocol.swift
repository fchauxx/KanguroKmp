import Foundation

public protocol GetFeatureFlagDataUseCaseProtocol {
    func execute(key: KanguroDataFeatureFlagKeys) throws -> Data
}
