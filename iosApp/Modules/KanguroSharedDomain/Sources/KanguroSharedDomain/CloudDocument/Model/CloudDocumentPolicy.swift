import Foundation

public struct CloudDocumentPolicy: Equatable {

    public let id: String?
    public let ciId: Int?
    public let policyStartDate: Date?
    public let policyAttachments: [PolicyAttachment]?
    public let policyDocuments: [PolicyDocumentData]?
    public let claimDocuments: [ClaimDocument]?

    public init(
        id: String?,
        ciId: Int?,
        policyStartDate: Date?,
        policyAttachments: [PolicyAttachment]?,
        policyDocuments: [PolicyDocumentData]?,
        claimDocuments: [ClaimDocument]?
    ) {
        self.id = id
        self.ciId = ciId
        self.policyStartDate = policyStartDate
        self.policyAttachments = policyAttachments
        self.policyDocuments = policyDocuments
        self.claimDocuments = claimDocuments
    }
}
