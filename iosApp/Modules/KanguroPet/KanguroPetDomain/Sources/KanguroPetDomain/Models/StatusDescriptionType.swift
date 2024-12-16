public enum StatusDescriptionType: String, Equatable, Codable {
    case Info
    case Warn
    case Error
    case Unknown
}

public extension StatusDescriptionType {
    init(value: String) {
        switch value.lowercased() {
        case "info": self = .Info
        case "warn": self = .Warn
        case "error" : self = .Error
        default: self = .Unknown
        }
    }
}
