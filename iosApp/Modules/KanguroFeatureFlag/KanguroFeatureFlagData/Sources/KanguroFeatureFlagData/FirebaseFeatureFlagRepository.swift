import KanguroFeatureFlagDomain
import FirebaseRemoteConfig

public class FirebaseFeatureFlagRepository: KanguroFeatureFlagRepositoryProtocol {

    var remoteConfig: RemoteConfig {
        FirebaseRemoteConfigSingleton.shared
    }

    public init() {}

    public func setDefaultValues(_ values: [String: NSObject]) throws {
        guard !values.isEmpty else {
            throw KanguroFeatureFlagError.emptyInput
        }
        remoteConfig.setDefaults(values)
    }

    public func getValue(for key: KanguroBoolFeatureFlagKeys) throws -> Bool {
        return remoteConfig.configValue(forKey: key.rawValue).boolValue
    }

    public func getValue(for key: KanguroIntFeatureFlagKeys) throws -> Int {
        return Int(truncating: remoteConfig.configValue(forKey: key.rawValue).numberValue)
    }

    public func getValue(for key: KanguroStringFeatureFlagKeys) throws -> String {
        guard let value = remoteConfig.configValue(forKey: key.rawValue).stringValue else {
            throw KanguroFeatureFlagError.notFound
        }
        return value
    }

    public func getValue(for key: KanguroDictionaryFeatureFlagKeys) throws -> Any {
        guard let value = remoteConfig.configValue(forKey: key.rawValue).jsonValue else {
            throw KanguroFeatureFlagError.notFound
        }
        return value
    }

    public func getValue(for key: KanguroFloatFeatureFlagKeys) throws -> Float {
        return Float(truncating: remoteConfig.configValue(forKey: key.rawValue).numberValue)
    }

    public func getValue(for key: KanguroDataFeatureFlagKeys) throws -> Data {
        return remoteConfig.configValue(forKey: key.rawValue).dataValue
    }
}
