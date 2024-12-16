import Foundation

public final class GetPolicyAttachment: GetPolicyAttachmentUseCaseProtocol {
    private let policyRepo: PolicyRepositoryProtocol

    public init(policyRepo: PolicyRepositoryProtocol) {
        self.policyRepo = policyRepo
    }

    public func execute(_ parameters: PolicyParameters, completion: @escaping ((Result<Policy, RequestError>) -> Void)) {
        policyRepo.getPolicy(parameters) { result in
            completion(result)
        }
    }

    public func execute(_ parameters: PolicyAttachmentParameters, completion: @escaping ((Result<Data, RequestError>) -> Void)) {
        policyRepo.getPolicyAttachment(parameters) { result in
            completion(result)
        }
    }
}
