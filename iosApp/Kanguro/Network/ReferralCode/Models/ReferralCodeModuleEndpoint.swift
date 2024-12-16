import Foundation
import KanguroSharedData
import KanguroNetworkDomain

enum ReferralCodeModuleEndpoint: Endpoint {
    
    case code(code: String)
    
    var path: String {
        switch self {
        case .code(let code):
            return "Discount/validate/\(code)"
        }
    }
}
