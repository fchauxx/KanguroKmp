import Foundation

public protocol GetPolicyUseCaseProtocol {
    func execute(
        _ parameters: PolicyParameters,
        completion: @escaping(
            (Result<Policy, RequestError>) -> Void)
        )
}
