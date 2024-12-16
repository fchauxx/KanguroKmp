import Foundation

public struct PetCloud: Equatable {
    public let id: Int?
    public let name: String?
    public let type: CloudType? = CloudType.pet
    public let cloudDocumentPolicies: [CloudDocumentPolicy]?

    public init(id: Int?, name: String?, cloudDocumentPolicies: [CloudDocumentPolicy]?) {
        self.id = id
        self.name = name
        self.cloudDocumentPolicies = cloudDocumentPolicies
    }

    public func toSelectedCloud() -> SelectedCloud {
        SelectedCloud(
            id: String(describing: self.id),
            name: self.name,
            type: self.type,
            cloudDocumentPolicies: self.cloudDocumentPolicies
        )
    }
}
