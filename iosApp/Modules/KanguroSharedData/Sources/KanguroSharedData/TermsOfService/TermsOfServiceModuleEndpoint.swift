import Foundation
import KanguroNetworkDomain

public enum TermsOfServiceModuleEndpoint: Endpoint {
    
    case termsOfService(language: String)
    
    public var path: String {
        switch self {
        case .termsOfService(let language):
            return "term/DownloadTerm?preferencialLanguage=\(language)"
        }
    }
}
