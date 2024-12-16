import Foundation
import KanguroNetworkDomain

public enum BankModuleEndpoint: Endpoint {
    
    case banks
    
    public var path: String {
        switch self {
        case .banks:
            return "Banks"
        }
    }
}
