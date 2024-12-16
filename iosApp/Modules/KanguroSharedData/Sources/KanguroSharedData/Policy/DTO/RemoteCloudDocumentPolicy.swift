import Foundation

public struct RemoteCloudDocumentPolicy: Codable, Equatable {

    public let id: String?
    public let ciId: Int?
    public let policyStartDate: String?
    public let policyAttachments: [RemotePolicyAttachment]?
    public let policyDocuments: [RemotePolicyDocumentData]?
    public let claimDocuments: [RemoteClaimDocument]?

    public init(
        id: String?,
        ciId: Int?,
        policyStartDate: String?,
        policyAttachments: [RemotePolicyAttachment]?,
        policyDocuments: [RemotePolicyDocumentData]?,
        claimDocuments: [RemoteClaimDocument]?
    ) {
        self.id = id
        self.ciId = ciId
        self.policyStartDate = policyStartDate
        self.policyAttachments = policyAttachments
        self.policyDocuments = policyDocuments
        self.claimDocuments = claimDocuments
    }
}
