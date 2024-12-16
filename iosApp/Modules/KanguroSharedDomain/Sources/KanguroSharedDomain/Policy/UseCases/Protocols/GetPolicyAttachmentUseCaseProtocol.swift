import Foundation

public protocol GetPolicyAttachmentUseCaseProtocol {
    func execute(
        _ parameters: PolicyAttachmentParameters,
        completion: @escaping ((Result<Data, RequestError>) -> Void)
    )
}
