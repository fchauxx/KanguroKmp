import Foundation
import KanguroSharedDomain

public typealias PetsClosure = ([Pet]) -> Void
public typealias PetClosure = (Pet) -> Void
public typealias OptionalPetClosure = (Pet?) -> Void
public typealias PetPoliciesClosure = ([PetPolicy]) -> Void
public typealias PetPolicyClosure = (PetPolicy) -> Void
public typealias CloudPetPolicyDocumentsOptionsClosure = (CloudDocumentPolicy, Pet) -> Void
public typealias CloudPetClaimsAndInvoicesListClosure = (String, [ClaimDocument], Pet) -> Void
public typealias CloudPetPolicyAttachmentsClosure = (String, [PolicyAttachment], Pet) -> Void
public typealias CloudPetPolicyDocumentsClosure = (String, [PolicyDocumentData], Pet) -> Void
public typealias CloudPetFilesClosure = (String, ClaimDocument, Pet) -> Void
