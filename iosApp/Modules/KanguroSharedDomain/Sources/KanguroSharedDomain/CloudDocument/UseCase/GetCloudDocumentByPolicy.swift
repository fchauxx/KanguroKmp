import Foundation

public final class GetCloudDocumentByPolicy: GetCloudDocumentsByPolicyUseCaseProtocol {

    let cloudDocumentRepo: any CloudDocumentRepositoryProtocol

    public init(cloudDocumentRepo: any CloudDocumentRepositoryProtocol) {
        self.cloudDocumentRepo = cloudDocumentRepo
    }

    public func execute(_ parameters: PolicyParameters, completion: @escaping ((Result<CloudDocumentPolicy, RequestError>) -> Void)) {
        cloudDocumentRepo.getCloudDocument(by: parameters) { result in
            completion(result)
        }
    }
}
