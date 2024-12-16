import UIKit
import KanguroSharedDomain
import KanguroPetDomain

enum ActionCardDataType {
    case standard
    case data(_ data: Any)
    case donation(_ isDonating: Bool)
    case directPayToVets(_ pets: [Pet])
    case cloudDocumentPolicies(_ selectedCloud: SelectedCloud)
    case cloudDocumentPolicy(_ cloudPolicy: KanguroSharedDomain.CloudDocumentPolicy, _ selectedCloud: SelectedCloud)
    case cloudClaimList(_ policyId: String, _ claimList: [KanguroSharedDomain.ClaimDocument], _ selectedCloud: SelectedCloud)
    case cloudPolicyAttachmentsFiles(_ id: String, _ policyAttachments: [KanguroSharedDomain.PolicyAttachment], _ selectedCloud: SelectedCloud)
    case cloudPolicyDocumentsFiles(_ id: String, _ policyDocuments: [KanguroSharedDomain.PolicyDocumentData], _ selectedCloud: SelectedCloud)
    case cloudClaimFiles(_ policyId: String, _ claimDocument: KanguroSharedDomain.ClaimDocument, _ selectedCloud: SelectedCloud)
    case attachmentFile(_ attachment: KanguroSharedDomain.Attachment)
    case policyAttachmentFile(_ policyAttachment: KanguroSharedDomain.PolicyAttachment)
}

struct ActionCardData {
    
    var leadingImage: UIImage?
    var traillingImage: UIImage?
    var leadingTitle: String
    var leadingSubtitle: String?
    
    var didTapAction: SimpleClosure?
    var didTapDonatingAction: BoolClosure?
    var didTapDataAction: AnyClosure?
    var didTapCloudActionCard: SelectedCloudClosure?
    var didTapDTPActionCard: PetsClosure?
    var didTapCloudPolicy: CloudPolicyDocumentsOptionsClosure?
    var didTapClaimAndInvoicesOption: CloudClaimsAndInvoicesListClosure?
    var didTapCloudPolicyAttachmentsFilesActionCard: CloudPolicyAttachmentsClosure?
    var didTapCloudPolicyDocumentsFilesActionCard: CloudPolicyDocumentsClosure?
    var didTapCloudClaimFilesActionCard: CloudFilesClosure?
    var didTapAttachmentFile: AttachmentClosure?
    var didTapPolicyAttachmentFile: PolicyAttachmentClosure?
    
    var viewType: ActionCardViewType? = .normal()
    var dataType: ActionCardDataType? = .standard
}
