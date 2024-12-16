import Foundation
import KanguroNetworkDomain

public enum VeterinarianModuleEndpoint: Endpoint {

    case getVeterinarians

    public var path: String {
        switch self {
        case .getVeterinarians:
            return "Veterinarians"
        }
    }
}
