import KanguroSharedDomain
import KanguroNetworkDomain

public enum ResetPasswordModuleEndpoint: Endpoint {
    
    case resetPassword
    
    public var path: String {
        switch self {
        case .resetPassword:
            return "user/resetPassword/sendEmail"
        }
    }
}
