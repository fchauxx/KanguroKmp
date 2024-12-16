import Foundation

public enum RemoteContactInformationType: String, Codable, Equatable {

    case text = "Text"
    case whatsapp = "Whatsapp"
    case sms = "Sms"
    case phone = "Phone"
}
