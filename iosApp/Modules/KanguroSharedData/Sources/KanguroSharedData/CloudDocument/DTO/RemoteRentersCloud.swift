import Foundation

public struct RemoteRentersCloud: Codable {
    public let id: String?
    public let name: String?
    public let cloudDocumentPolicies: [RemoteCloudDocumentPolicy]?

    public init(id: String?, name: String?, cloudDocumentPolicies: [RemoteCloudDocumentPolicy]?) {
        self.id = id
        self.name = name
        self.cloudDocumentPolicies = cloudDocumentPolicies
    }
}
