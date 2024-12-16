import Foundation

public enum AdditionalPartieType: String {
    
    case Spouse
    case Child
    case Roommate
    case Landlord
    case PropertyManager
}

public struct AdditionalPartie: Identifiable {
    
    public let id: String?
    public let type: AdditionalPartieType?
    public let fullName: String?
    public let email: String?
    public let birthDate: String?
    public let address: Address?
    
    public init(id: String? = nil,
                type: AdditionalPartieType? = nil,
                fullName: String? = nil,
                email: String? = nil,
                birthDate: String? = nil,
                address: Address? = nil) {
        self.id = id
        self.type = type
        self.fullName = fullName
        self.email = email
        self.birthDate = birthDate
        self.address = address
    }
}
