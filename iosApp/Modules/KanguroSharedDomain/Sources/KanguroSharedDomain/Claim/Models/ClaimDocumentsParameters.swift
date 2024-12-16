import Foundation

public struct ClaimDocumentsParameters {

    public var policyId: String
    public var claimId: String

    public init(policyId: String, claimId: String) {
        self.policyId = policyId
        self.claimId = claimId
    }
}
