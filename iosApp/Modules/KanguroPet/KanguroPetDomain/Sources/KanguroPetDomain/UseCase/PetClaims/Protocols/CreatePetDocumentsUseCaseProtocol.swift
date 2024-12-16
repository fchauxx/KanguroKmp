import Foundation
import KanguroSharedDomain

public protocol CreatePetDocumentsUseCaseProtocol {

    func execute(
        _ parameters: UploadPetAttachmentParameters,
        completion: @escaping ((Result<[Int], RequestError>) -> Void)
    )
}
