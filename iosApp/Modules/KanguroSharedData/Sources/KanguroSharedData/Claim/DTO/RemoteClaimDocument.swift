import Foundation

public struct RemoteClaimDocument: Codable, Equatable {

    public var claimPrefixId: String?
    public var claimId: String?
    public var claimDocuments: [RemoteAttachment]?

    public init(
        claimPrefixId: String? = nil,
        claimId: String? = nil,
        claimDocuments: [RemoteAttachment]? = nil
    ) {
        self.claimPrefixId = claimPrefixId
        self.claimId = claimId
        self.claimDocuments = claimDocuments
    }
}
