import Foundation
import KanguroNetworkDomain

public enum LanguageModuleEndpoint: Endpoint {
    
    case language
    
    public var path: String {
        switch self {
        case .language:
            return "user/preferredLanguage"
        }
    }
}
