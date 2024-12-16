import Foundation

public enum RemoteInformerDataType: String, Codable {
    
    case NewPetParent
    case VetAdvice
    case FAQ
}

public enum RemoteKanguroParameterType: String, Codable {
    
    case VA_D
    case VA_C
    case FAQ
    case FAQPartner
    case ETP
    case STP
    case NPP_D
    case NewPetParent
    case VetAdvice
    case FAQRenters
}

public struct RemoteInformerData: Codable {
    
    public var key: Int?
    public var value: String?
    public var description: String?
    public var type: RemoteKanguroParameterType?
    public var language: RemoteLanguage?
    public var isActive: Bool?
    
    public init(key: Int? = nil,
                value: String? = nil,
                description: String? = nil,
                type: RemoteKanguroParameterType? = nil,
                language: RemoteLanguage? = nil,
                isActive: Bool? = nil) {
        self.key = key
        self.value = value
        self.description = description
        self.type = type
        self.language = language
        self.isActive = isActive
    }
}
