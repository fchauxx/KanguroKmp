import Foundation
import KanguroSharedDomain

public protocol CreateDirectPaymentVeterinarianSignatureUseCaseProtocol {

    func execute(
        claimId: String,
        parameters: UploadedDocumentParameters,
        completion: @escaping ((Result<Void, RequestError>) -> Void)
    )
}
