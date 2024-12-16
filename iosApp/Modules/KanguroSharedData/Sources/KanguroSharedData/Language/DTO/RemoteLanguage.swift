import Foundation

public enum RemoteLanguage: String, Codable {
    case English = "en"
    case Spanish = "es"

    // MARK: - Initializers
    public init(from decoder: Decoder) throws {
        let value = try decoder.singleValueContainer().decode(String.self)
        switch value {
        case "en", "English": self = .English
        case "es", "Spanish": self = .Spanish
        default: self = .English
        }
    }

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
