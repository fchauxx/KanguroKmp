import Foundation

public struct RentersCloud: Equatable {
    public let id: String?
    public let name: String?
    public let type: CloudType? = CloudType.renters
    public let cloudDocumentPolicies: [CloudDocumentPolicy]?

    public init(id: String?, name: String?, cloudDocumentPolicies: [CloudDocumentPolicy]?) {
        self.id = id
        self.name = name
        self.cloudDocumentPolicies = cloudDocumentPolicies
    }

    public func toSelectedCloud() -> SelectedCloud {
        SelectedCloud(
            id: self.id,
            name: self.name,
            type: self.type,
            cloudDocumentPolicies: self.cloudDocumentPolicies
        )
    }
}
