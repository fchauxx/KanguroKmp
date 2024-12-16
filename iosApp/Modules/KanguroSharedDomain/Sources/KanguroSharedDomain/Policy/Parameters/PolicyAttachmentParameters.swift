import Foundation

public struct PolicyAttachmentParameters {

    public var policyId: String
    public var attachment: PolicyAttachment

    public init(policyId: String, attachment: PolicyAttachment) {
        self.policyId = policyId
        self.attachment = attachment
    }
}
