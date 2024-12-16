import Foundation
import KanguroSharedDomain

public protocol GetAdditionalPartiesUseCaseProtocol {
    func execute(
        policyId: String,
        completion: @escaping ((Result<[AdditionalPartie], RequestError>) -> Void)
    )
}
