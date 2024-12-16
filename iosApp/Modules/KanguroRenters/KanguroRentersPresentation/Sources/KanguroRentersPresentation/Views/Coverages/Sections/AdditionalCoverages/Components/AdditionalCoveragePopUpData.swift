import Foundation

public enum AdditionalCoveragePopUpData {

    case waterSewer
    case fraudProtection
    case replacementCost

    var title: String {
        switch self {
        case .waterSewer:
            return "waterProtection.popup.title"
        case .fraudProtection:
            return "fraudProtection.popup.title"
        case .replacementCost:
            return "replacementCost.popup.title"
        }
    }

    var description: String {
        switch self {
        case .waterSewer:
            return "waterProtection.popup.description"
        case .fraudProtection:
            return "fraudProtection.popup.description"
        case .replacementCost:
            return "replacementCost.popup.description"
        }
    }
}
