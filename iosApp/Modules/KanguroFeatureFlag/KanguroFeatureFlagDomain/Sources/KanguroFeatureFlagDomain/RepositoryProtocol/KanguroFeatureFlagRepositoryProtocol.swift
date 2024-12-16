import Foundation

public protocol KanguroFeatureFlagRepositoryProtocol {
    func setDefaultValues(_ values: [String: NSObject]) throws
    func getValue(for key: KanguroBoolFeatureFlagKeys) throws -> Bool
    func getValue(for key: KanguroIntFeatureFlagKeys) throws -> Int
    func getValue(for key: KanguroStringFeatureFlagKeys) throws -> String
    func getValue(for key: KanguroDictionaryFeatureFlagKeys) throws -> Any
    func getValue(for key: KanguroFloatFeatureFlagKeys) throws -> Float
    func getValue(for key: KanguroDataFeatureFlagKeys) throws -> Data
}
