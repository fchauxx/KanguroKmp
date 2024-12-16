import Foundation

public protocol ModelMapper {
    associatedtype T
    static func map<T>(_ input: some Codable) throws -> T
}
