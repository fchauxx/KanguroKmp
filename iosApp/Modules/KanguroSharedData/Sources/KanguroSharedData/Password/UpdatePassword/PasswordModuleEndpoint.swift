import KanguroSharedDomain
import KanguroNetworkDomain

public enum PasswordModuleEndpoint: Endpoint {
    
    case password
    
    public var path: String {
        switch self {
        case .password:
            return "user/updatePassword"
        }
    }
}
