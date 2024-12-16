import Foundation


public protocol GetPolicyRenterDocumentUseCaseProtocol {
    func execute(
        _ parameters: PolicyDocumentParameters,
        completion: @escaping(
            (Result<Data, RequestError>) -> Void)
        )
}
