import Foundation

public enum RemoteAdditionalPartieType: String, Codable {
    
    case Spouse
    case Child
    case Roommate
    case Landlord
    case PropertyManager
}

public struct RemoteAdditionalPartie: Codable {
    
    public let id: String?
    public let type: RemoteAdditionalPartieType?
    public let fullName: String?
    public let email: String?
    public let birthDate: String?
    public let address: RemoteAddress?
    
    public init(id: String? = nil,
                type: RemoteAdditionalPartieType? = nil,
                fullName: String? = nil,
                email: String? = nil,
                birthDate: String? = nil,
                address: RemoteAddress? = nil) {
        self.id = id
        self.type = type
        self.fullName = fullName
        self.email = email
        self.birthDate = birthDate
        self.address = address
    }
}
