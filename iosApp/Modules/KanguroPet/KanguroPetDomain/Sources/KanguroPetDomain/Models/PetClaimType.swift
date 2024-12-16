import Foundation

public enum PetClaimType: String, Equatable {
    case Illness
    case Accident
    case Other

    public var es_localization: String {
        switch self {

        case .Illness:
            return "Enfermedad"
        case .Accident:
            return "Accidente"
        case .Other:
            return "Otro"
        }
    }
}
