import Foundation

public protocol GetPoliciesUseCaseProtocol {
    func execute(
        completion: @escaping(
            (Result<[Policy], RequestError>) -> Void)
        )
}

