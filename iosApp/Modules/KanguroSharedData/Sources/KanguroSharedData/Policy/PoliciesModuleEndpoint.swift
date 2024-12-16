import KanguroSharedDomain
import KanguroNetworkDomain

public enum PoliciesModuleEndpoint: Endpoint {

    case policies
    case policy(policyId: String)
    case attachment(id: String, attachmentId: Int)
    case documents(policyId: String)
    case document(policyId: String, documentId: Int64, filename: String)
    case renterDocument(policyId: String, documentId: Int64, filename: String)
    case coverages(policyId: String, offerId: Int?, reimbursement: Double)

    public var path: String {
        switch self {
        case .policies:
            return "policy"
        case .policy(let id):
            return "policy/\(id)"
        case .attachment(let id, let attachmentId):
            return "policy/\(id)/attachments/\(attachmentId)"
        case .documents(let id):
            return "policy/\(id)/documents"
        case .document(let policyId, let documentId, let filename):
            return "policy/\(policyId)/documents/\(documentId)?filename=\(filename)"
        case .renterDocument(let policyId, let documentId, let filename):
            return "renters/policy/\(policyId)/documents/\(documentId)?filename=\(filename)"
        case .coverages(let id, let offerId, let reimbursement):
            guard let offerId else {
                return "policy/\(id)/coverage/?reimbursement=\(reimbursement)"
            }
            return "policy/\(id)/coverage/?offerId=\(offerId)&reimbursement=\(reimbursement)"
        }
    }
}

