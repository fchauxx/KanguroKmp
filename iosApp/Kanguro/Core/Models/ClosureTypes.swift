import UIKit
import KanguroRentersDomain
import MapKit
import KanguroSharedData
import KanguroSharedDomain
import KanguroPetDomain
import KanguroNetworkDomain

typealias SimpleClosure = () -> Void
typealias StringClosure = (String) -> Void
typealias StringOptionalClosure = (String?) -> Void
typealias IntClosure = (Int) -> Void
typealias DoubleOptionalClosure = (Double?) -> Void
typealias CGFloatClosure = (CGFloat) -> Void
typealias AnyClosure = (Any) -> Void
typealias ImageClosure = (UIImage) -> Void
typealias AlertButtonActionClosure = ((UIAlertAction) -> Void)?
typealias TextFieldPickerDataClosure = (TextFieldPickerData) -> Void
typealias TextFieldCustomPickerDataClosure = (CustomPickerData) -> Void
typealias CGPointClosure = (CGPoint) -> Void
typealias PetsClosure = ([Pet]) -> Void
typealias PetClosure = (Pet) -> Void
typealias SelectedCloudClosure = (SelectedCloud) -> Void
typealias OptionalPetClosure = (Pet?) -> Void
typealias BoolClosure = (Bool) -> Void
typealias NextStepClosure = (NextStepParameters?) -> Void
typealias LanguageClosure = (Language) -> Void
typealias PetPoliciesClosure = ([PetPolicy]) -> Void
typealias PetPolicyClosure = (PetPolicy) -> Void
typealias DataClosure = (Data) -> Void
typealias RemindersClosure = ([KanguroSharedDomain.Reminder]) -> Void
typealias VetAdviceTypeClosure = (VetAdviceType) -> Void
typealias PolicyDocumentDataClosure = (KanguroSharedDomain.PolicyDocumentData) -> Void
typealias ClaimClosure = (PetClaim) -> Void
typealias CheckboxDataClosure = (CheckboxLabelData) -> Void
typealias SelectedCheckboxDataClosure = ([CheckboxLabelData]) -> Void
typealias NewPetVetDirectPayClaimClosure = (PetVetDirectPaymentParameters) -> Void
typealias VetDataClosure = (Veterinarian) -> Void
typealias UserPolicyTypeClosure = (UserPolicyType) -> Void
typealias RenterPolicyClosure = (RenterPolicy) -> Void
typealias OnboardingChatClosure = (RenterPolicy, Bool) -> Void

typealias DonationTypeClosure = (DonationType, [DonationCause])-> Void
typealias DonationCauseSelectedTypeClosure = (DonationCauseSelected) -> Void
typealias StatusClosure = (Status) -> Void
typealias LocationClosure = (MKMapItem) -> Void
typealias PreventiveItemClosure = (PreventiveItem) -> Void
typealias BoolResponseClosure = (Bool, NetworkRequestError?) -> Void
typealias TwoStringClosure = (String, String) -> Void
typealias AttachmentClosure = (KanguroSharedDomain.Attachment) -> Void
typealias TemporaryFileClosure = (TemporaryFile) -> Void
typealias PolicyAttachmentClosure = (KanguroSharedDomain.PolicyAttachment) -> Void
typealias CloudPolicyDocumentsOptionsClosure = (CloudDocumentPolicy, SelectedCloud) -> Void
typealias CloudClaimsAndInvoicesListClosure = (String, [ClaimDocument], SelectedCloud) -> Void
typealias CloudPolicyAttachmentsClosure = (String, [PolicyAttachment], SelectedCloud) -> Void
typealias CloudPolicyDocumentsClosure = (String, [KanguroSharedDomain.PolicyDocumentData], SelectedCloud) -> Void
typealias CloudFilesClosure = (String, ClaimDocument, SelectedCloud) -> Void
