import Foundation
import KanguroNetworkDomain

public enum ExternalLinksEndpoint: Endpoint {
    
    case partner(partnerName: String, userId: String)

    public var path: String {
        switch self {
        case .partner(let partnerName, let userId):
            return "ExternalLinks/\(partnerName)?userId=\(userId)"
        }
    }
}
