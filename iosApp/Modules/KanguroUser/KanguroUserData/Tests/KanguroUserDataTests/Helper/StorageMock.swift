import Foundation
import KanguroStorageDomain

final class StorageMock: SecureStorage {

    var persistance: [String: Any?] = [:]

    @discardableResult
    func save<T>(value: T, key: String) -> Bool {
        persistance[key] = value
        return true
    }

    func get<T>(key: String) -> T? {
        guard let result = persistance[key] as? T else {
            return nil
        }
        return result
    }

    func remove(key: String) {
        persistance[key] = nil
    }

    @discardableResult
    func cleanAll() -> Bool {
        persistance = [:]
        return true
    }
}
