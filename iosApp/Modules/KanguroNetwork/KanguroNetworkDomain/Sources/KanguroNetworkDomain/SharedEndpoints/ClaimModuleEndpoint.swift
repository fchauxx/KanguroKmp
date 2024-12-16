import Foundation

public enum ClaimModuleEndpoint: Endpoint {

    case claims
    case claim(id: String)
    case attachments(claimId: String)
    case attachment(claimId: String, attachmentId: Int)
    case feedback(claimId: String)
    case communications(claimId: String)
    case postClaims
    case postUploadDocuments
    case createPetVetDirectPaymentClaim
    case createDirectPaymentVetSignature(claimId: String)
    case getDirectPaymentPreSignedFileUrl(claimId: String)

    private var apiVersion: String {
        "v2"
    }

    public var path: String {
        switch self {
        case .claims:
            return "\(apiVersion)/claims/"
        case .claim(let id):
            return "\(apiVersion)/claims/\(id)"
        case .attachments(let id):
            return "claims/\(id)/documents"
        case .attachment(let id, let attachmentId):
            return "claims/\(id)/documents/\(attachmentId)"
        case .feedback(let id):
            return "claims/\(id)/feedback"
        case .communications(let id):
            return "claims/\(id)/communications"
        case .postClaims:
            return "claims"
        case .postUploadDocuments:
            return "claims/uploadDocuments"
        case .createPetVetDirectPaymentClaim:
            return "claims/DirectPayment"
        case .createDirectPaymentVetSignature(let claimId):
            return "claims/\(claimId)/DirectPaymentVeterinarianSignature"
        case .getDirectPaymentPreSignedFileUrl(let claimId):
            return "/claims/\(claimId)/DirectPaymentPreSignedFileUrl"
        }
    }
}
