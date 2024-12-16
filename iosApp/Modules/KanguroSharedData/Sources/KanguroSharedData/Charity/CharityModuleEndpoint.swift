import Foundation
import KanguroNetworkDomain

public enum CharityModuleEndpoint: Endpoint {
    
    case charity
    
    public var path: String {
        switch self {
        case .charity:
            return "Charity/GetAll"
        }
    }
}
