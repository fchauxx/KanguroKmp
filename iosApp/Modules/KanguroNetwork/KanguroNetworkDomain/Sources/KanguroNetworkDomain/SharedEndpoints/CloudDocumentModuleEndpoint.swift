import Foundation

public enum CloudDocumentModuleEndpoint: Endpoint {

    case CloudDocuments
    case CloudDocumentsByPolicyId(policyId: String)
    case ClaimDocumentsById(policyId: String, claimId: String)

    public var path: String {
        switch self {
        case .CloudDocuments:
            return "CloudDocuments"
        case .CloudDocumentsByPolicyId(let policyId):
            return "CloudDocuments/\(policyId)"
        case .ClaimDocumentsById(let policyId, let claimId):
            return "CloudDocuments/\(policyId)/\(claimId)"
        }
    }
}
