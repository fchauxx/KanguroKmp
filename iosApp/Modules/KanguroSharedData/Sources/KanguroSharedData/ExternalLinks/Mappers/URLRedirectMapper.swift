import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public struct URLRedirectMapper: ModelMapper {
    public typealias T = URLRedirect

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteURLRedirect = input as? RemoteURLRedirect else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        
        guard let urlRedirect = URLRedirect(
            redirectTo: input.redirectTo
        ) as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return urlRedirect
    }
}
