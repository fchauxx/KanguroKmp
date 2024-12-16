import Foundation
import KanguroNetworkDomain

public enum PetModuleEndpoint: Endpoint {

    case getPets
    case getPet(id: Int)
    case putPetPicture

    public var path: String {
        switch self {
        case .getPets:
            return "pets"
        case .getPet(let id):
            return "pets/\(id)"
        case .putPetPicture:
            return "pets/picture"
        }
    }
}
