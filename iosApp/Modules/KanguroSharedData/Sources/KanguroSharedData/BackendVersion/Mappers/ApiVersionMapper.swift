import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public struct ApiVersionMapper: ModelMapper {
    public typealias T = KanguroSharedDomain.ApiVersion

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteApiVersion = input as? RemoteApiVersion else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        
        guard let apiVersion = ApiVersion(version: input.version) as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return apiVersion
    }
}
