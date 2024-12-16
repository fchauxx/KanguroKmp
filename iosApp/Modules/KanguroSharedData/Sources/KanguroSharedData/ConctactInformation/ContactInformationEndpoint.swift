import Foundation
import KanguroNetworkDomain

public enum ContactInformationEndpoint: Endpoint {

    case getContactInformation

    public var path: String {
        switch self {
        case .getContactInformation:
            return "ContactInformation"
        }
    }
}
