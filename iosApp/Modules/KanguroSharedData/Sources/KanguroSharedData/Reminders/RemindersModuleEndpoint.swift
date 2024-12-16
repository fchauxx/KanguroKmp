import Foundation
import KanguroNetworkDomain

public enum RemindersModuleEndpoint: Endpoint {
    
    case reminder
    
    public var path: String {
        switch self {
        case .reminder:
            return "user/reminders"
        }
    }
}
