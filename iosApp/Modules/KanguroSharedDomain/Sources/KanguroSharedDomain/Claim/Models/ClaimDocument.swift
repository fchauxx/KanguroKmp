import Foundation

public struct ClaimDocument: Equatable {

    public var claimPrefixId: String?
    public var claimId: String?
    public var claimDocuments: [Attachment]?

    public init(
        claimPrefixId: String? = nil,
        claimId: String? = nil,
        claimDocuments: [Attachment]? = nil
    ) {
        self.claimPrefixId = claimPrefixId
        self.claimId = claimId
        self.claimDocuments = claimDocuments
    }
}
