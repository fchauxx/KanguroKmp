import Foundation
import KanguroNetworkDomain

public enum BackendVersionModuleEndpoint: Endpoint {
    
    case version
    
    public var path: String {
        switch self {
        case .version:
            return "Version"
        }
    }
}
