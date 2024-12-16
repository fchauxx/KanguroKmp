import KanguroSharedDomain
import KanguroNetworkDomain

enum KeycloakModuleEndpoint: Endpoint {
    
    case keycloak
    
    var path: String {
        switch self {
        case .keycloak:
            return "keycloack"
        }
    }
}
