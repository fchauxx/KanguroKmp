import KanguroSharedDomain
import KanguroNetworkDomain

public enum ProfileModuleEndpoint: Endpoint {
    
    case profile
    
    public var path: String {
        switch self {
        case .profile:
            return "user/updateUserProfile"
        }
    }
}
