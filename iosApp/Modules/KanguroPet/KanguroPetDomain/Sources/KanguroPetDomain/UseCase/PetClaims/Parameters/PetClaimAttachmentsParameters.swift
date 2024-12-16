import Foundation
import KanguroSharedDomain

public struct PetClaimAttachmentsParameters {

    public var claimId: String
    public var attachment: Attachment

    public init(
        claimId: String,
        attachment: Attachment
    ) {
        self.claimId = claimId
        self.attachment = attachment
    }
}
