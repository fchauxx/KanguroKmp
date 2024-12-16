import KanguroStorageDomain
import Foundation

public struct SecureStorageImplementation: SecureStorage {
    
    // MARK: - Stored Properties
    private let keychain: KeychainWrapper = KeychainWrapper.standard

    public init() { }
}

// MARK: - Public Methods
extension SecureStorageImplementation {

    @discardableResult
    public func save<T: Codable>(value: T, key: String) -> Bool {
        if let data = try? JSONEncoder().encode(value) {
            keychain.set(data, forKey: key)
            return true
        }
        return false
    }

    public func get<T: Codable>(key: String) -> T? {
        if let data = keychain.data(forKey: key) {
            let value = try? JSONDecoder().decode(T.self, from: data)
            return value
        }
        return nil
    }

    public func remove(key: String) {
        keychain.removeObject(forKey: key)
    }

    @discardableResult
    public func cleanAll() -> Bool {
        return keychain.removeAllKeys()
    }
}
