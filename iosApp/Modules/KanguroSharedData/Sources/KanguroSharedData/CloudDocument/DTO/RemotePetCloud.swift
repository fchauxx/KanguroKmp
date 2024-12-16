import Foundation

public struct RemotePetCloud: Codable {
    public let id: Int?
    public let name: String?
    public let cloudDocumentPolicies: [RemoteCloudDocumentPolicy]?

    public init(id: Int?, name: String?, cloudDocumentPolicies: [RemoteCloudDocumentPolicy]?) {
        self.id = id
        self.name = name
        self.cloudDocumentPolicies = cloudDocumentPolicies
    }
}
