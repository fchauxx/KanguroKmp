import Foundation

public protocol SecureStorage {
    @discardableResult
    func save<T: Codable>(value: T, key: String) -> Bool

    func get<T: Codable>(key: String) -> T?

    func remove(key: String)

    @discardableResult
    func cleanAll() -> Bool
}
