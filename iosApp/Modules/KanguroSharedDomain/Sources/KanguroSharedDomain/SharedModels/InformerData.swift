import Foundation

public enum InformerDataType: String {
    
    case NewPetParent
    case VetAdvice
    case FAQ
    case FAQPartner
    case FAQRenters
}

public enum KanguroParameterType: String {
    
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

public struct InformerData: Equatable {
    
    public var key: Int?
    public var value: String?
    public var description: String?
    public var type: KanguroParameterType?
    public var language: Language?
    public var isActive: Bool?
    
    public init(key: Int? = nil,
                value: String? = nil,
                description: String? = nil,
                type: KanguroParameterType? = nil,
                language: Language? = nil,
                isActive: Bool? = nil) {
        self.key = key
        self.value = value
        self.description = description
        self.type = type
        self.language = language
        self.isActive = isActive
    }
}
