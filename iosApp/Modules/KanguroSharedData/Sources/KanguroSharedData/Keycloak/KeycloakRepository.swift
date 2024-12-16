import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public class KeycloakRepository: KeycloakRepositoryProtocol {

    private let network: NetworkProtocol
    
    public init(network: NetworkProtocol) {
        self.network = network
    }
    
    public func getKeycloak(completion: @escaping ((Result<KeycloakResponse, RequestError>) -> Void)) {
        
        network.request(endpoint: KeycloakModuleEndpoint.keycloak,
                        method: .GET,
                        responseType: RemoteKeycloakResponse.self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: KeycloakMapper(), 
                                   response: response,
                                   completion: completion)
        }
    }
}
