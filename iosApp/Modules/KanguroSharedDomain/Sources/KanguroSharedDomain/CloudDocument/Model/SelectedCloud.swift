import Foundation

public struct SelectedCloud: Equatable {
    public let id: String?
    public let name: String?
    public let type: CloudType?
    public let cloudDocumentPolicies: [CloudDocumentPolicy]?

    public init(id: String?, name: String?, type: CloudType?, cloudDocumentPolicies: [CloudDocumentPolicy]?) {
        self.id = id
        self.name = name
        self.type = type
        self.cloudDocumentPolicies = cloudDocumentPolicies
    }
}

public enum CloudType: String, Codable {
    case pet = "PET"
    case renters = "RENTERS"
}
