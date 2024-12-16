import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public struct KeycloakMapper: ModelMapper {
    public typealias T = KeycloakResponse
    
    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteKeycloakResponse = input as? RemoteKeycloakResponse else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        
        let keycloakResponse = KeycloakResponse(access_token: input.access_token,
                                                expired_in: input.expired_in,
                                                refresh_token: input.refresh_token,
                                                token_type: input.token_type,
                                                session_state: input.session_state,
                                                scope: input.scope)
        guard let result: T = keycloakResponse as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
