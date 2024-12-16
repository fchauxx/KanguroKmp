import Foundation
import KanguroStorageDomain

public struct StorageImplementation: Storage {

    // MARK: - Stored Properties
    private let userDefaults: UserDefaults

    public init(_ userDefaults: UserDefaults = .standard) {
        self.userDefaults = userDefaults
    }
}

// MARK: - Public Methods
extension StorageImplementation {

    @discardableResult
    public func save<T: Encodable>(value: T, key: String) -> Bool {
        if let data = try? JSONEncoder().encode(value) {
            userDefaults.set(data, forKey: key)
            return true
        }
        return false
    }

    @discardableResult
    public func get<T: Decodable>(key: String) -> T? {
        if let data = userDefaults.data(forKey: key) {
            let value = try? JSONDecoder().decode(T.self, from: data)
            return value
        }
        return nil
    }

    public func remove(key: String) {
        userDefaults.set(nil, forKey: key)
    }

    @discardableResult
    public func cleanAll() -> Bool {
        let dict = userDefaults.dictionaryRepresentation()
        dict.keys.forEach { userDefaults.removeObject(forKey: $0) }
        return true
    }
}
