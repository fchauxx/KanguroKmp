import Foundation

public protocol PolicyRepositoryProtocol {
    func getPolicies(
        completion: @escaping ((Result<[Policy],RequestError>) -> Void)
    )

    func getPolicy(
        _ parameters: PolicyParameters,
        completion: @escaping ((Result<Policy, RequestError>) -> Void)
    )

    func getPolicyAttachment(
        _ parameters: PolicyAttachmentParameters,
        completion: @escaping ((Result<Data, RequestError>) -> Void)
    )

    func getPolicyDocuments(
        _ parameters: PolicyParameters,
        completion: @escaping(
            (Result<[PolicyDocumentData],RequestError>) -> Void)
    )

    func getPolicyDocument(
        _ parameters: PolicyDocumentParameters,
        completion: @escaping(
            (Result<Data,RequestError>) -> Void)
    )
    
    func getPolicyRenterDocument(
        _ parameters: PolicyDocumentParameters,
        completion: @escaping(
            (Result<Data,RequestError>) -> Void)
    )

    func getCoverages(
        _ parameters: PolicyCoverageParameters,
        completion: @escaping(
            (Result<[CoverageData],RequestError>) -> Void)
    )
}
