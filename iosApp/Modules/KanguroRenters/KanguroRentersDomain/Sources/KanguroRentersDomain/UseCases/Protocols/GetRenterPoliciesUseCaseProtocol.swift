import Foundation
import KanguroSharedDomain

public protocol GetRenterPoliciesUseCaseProtocol {

    func execute(
        completion: @escaping ((Result<[RenterPolicy], RequestError>) -> Void)
    )
}
