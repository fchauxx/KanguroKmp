import Foundation
import KanguroSharedDomain

public protocol GetRenterPolicyUseCaseProtocol {

    func execute(
        id: String,
        completion: @escaping ((Result<RenterPolicy, RequestError>) -> Void)
    )
}
