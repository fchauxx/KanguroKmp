import Foundation
import KanguroSharedDomain

public protocol PetClaimRepositoryProtocol {
    func getClaims(
        completion: @escaping ((Result<[PetClaim], RequestError>) -> Void)
    )

    func getClaim(
        _ parameters: PetClaimParameters,
        completion: @escaping ((Result<PetClaim, RequestError>) -> Void)
    )

    func getClaimAttachments(
        _ parameters: PetClaimParameters,
        completion: @escaping ((Result<[Attachment], RequestError>) -> Void)
    )

    func getClaimAttachment(
        _ parameters: PetClaimAttachmentsParameters,
        completion: @escaping  ((Result<Data, RequestError>) -> Void)
    )

    func updateFeedback(
        _ parameters: PetClaimParameters,
        feedback: PetFeedbackDataParameters,
        completion: @escaping  ((Result<Void, RequestError>) -> Void)
    )

    func getCommunications(
        _ parameters: PetClaimParameters,
        completion: @escaping  ((Result<[Communication], RequestError>) -> Void)
    )

    func getDirectPaymentPreSignedFileURL(
        claimId: String,
        completion: @escaping  ((Result<String, RequestError>) -> Void)
    )

    func createCommunications(
        _ parameters: PetCommunicationParameters,
        completion: @escaping  ((Result<[Communication], RequestError>) -> Void)
    )

    func createDocuments(
        _ parameters: UploadPetAttachmentParameters,
        completion: @escaping ((Result<[Int], RequestError>) -> Void)
    )

    func createClaim(
        _ parameters: NewPetClaimParameters,
        completion: @escaping ((Result<PetClaim, RequestError>) -> Void)
    )

    func createPetVetDirectPaymentClaim(
        _ parameters: PetVetDirectPaymentParameters,
        completion: @escaping ((Result<PetClaim, RequestError>) -> Void)
    )

    func createDirectPaymentVeterinarianSignature(
        claimId: String,
        parameters: UploadedDocumentParameters,
        completion: @escaping ((Result<Void, RequestError>) -> Void)
    )
}
