import Foundation

public enum Language: String {
    case English = "en"
    case Spanish = "es"

    // MARK: - Computed Properties
    public var title: String {
        switch self {
        case .English:
            return "English"
        case .Spanish:
            return "Spanish"
        }
    }
}
