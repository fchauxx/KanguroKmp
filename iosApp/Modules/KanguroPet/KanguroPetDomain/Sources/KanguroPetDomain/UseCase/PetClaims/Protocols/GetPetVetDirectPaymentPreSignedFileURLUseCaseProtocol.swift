import Foundation
import KanguroSharedDomain

public protocol GetPetVetDirectPaymentPreSignedFileURLUseCaseProtocol {

    func execute(
        _ claimId: String,
        completion: @escaping ((Result<String, RequestError>) -> Void)
    )
}
