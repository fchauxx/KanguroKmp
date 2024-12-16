import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain
import KanguroNetworkDomain

public class PolicyRepository: PolicyRepositoryProtocol {
    
    private let network: NetworkProtocol
    
    public init(network: NetworkProtocol) {
        self.network = network
    }
    
    public func getPolicies(
        completion: @escaping ((Result<[Policy], RequestError>) -> Void)
    ) {
        network.request(endpoint: PoliciesModuleEndpoint.policies,
                        method: .GET,
                        responseType: [RemotePolicy].self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: PoliciesMapper(), 
                                   response: response,
                                   completion: completion)
        }
    }
    
    public func getPolicy(_ parameters: PolicyParameters,
                          completion: @escaping ((Result<Policy, RequestError>) -> Void)) {
        network.request(
            endpoint: PoliciesModuleEndpoint.policy(
                policyId: parameters.id
            ),
            method: .GET,
            responseType: RemotePolicy.self,
            errorType: NetworkRequestError.self
        ) { response in
            ResponseHandler.handle(mapper: PolicyMapper(), 
                                   response: response,
                                   completion: completion)
        }
        
    }
    
    public func getPolicyDocuments(
        _ parameters: PolicyParameters,
        completion: @escaping ((Result<[PolicyDocumentData], RequestError>) -> Void)
    ) {
        network.request(
            endpoint: PoliciesModuleEndpoint.documents(policyId: parameters.id),
            method: .GET,
            responseType: [RemotePolicyDocumentData].self,
            errorType: NetworkRequestError.self
        ) { response in
            ResponseHandler.handle(mapper: PolicyDocumentsMapper(), 
                                   response: response,
                                   completion: completion)
        }
    }
    
    public func getPolicyAttachment(_ parameters: PolicyAttachmentParameters,
                                    completion: @escaping ((Result<Data, RequestError>) -> Void)) {
        network.download(
            endpoint: PoliciesModuleEndpoint.attachment(
                id: parameters.policyId,
                attachmentId: parameters.attachment.id
            ),
            fileName: parameters.attachment.name.getPrefixBeforeDot ?? "TemporaryPDF",
            fileExt: parameters.attachment.name.getFileExtension ?? "pdf",
            errorType: NetworkRequestError.self
        ) { response in
            switch response {
            case .success(let data):
                completion(.success(data))
            case .failure(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            case .customError(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            }
        }
    }
    
    public func getPolicyDocument(_ parameters: PolicyDocumentParameters,
                                  completion: @escaping ((Result<Data, RequestError>) -> Void)) {
        network.download(
            endpoint: PoliciesModuleEndpoint.document(
                policyId: parameters.policyId,
                documentId: parameters.documentId,
                filename: parameters.filename
            ),
            fileName: parameters.filename,
            fileExt: parameters.filename.getFileExtension ?? "pdf",
            errorType: NetworkRequestError.self
        ) { response in
            switch response {
            case .success(let data):
                completion(.success(data))
            case .failure(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            case .customError(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            }
        }
    }
    
    public func getPolicyRenterDocument(_ parameters: PolicyDocumentParameters,
                                  completion: @escaping ((Result<Data, RequestError>) -> Void)) {
        network.download(
            endpoint: PoliciesModuleEndpoint.renterDocument(
                policyId: parameters.policyId,
                documentId: parameters.documentId,
                filename: parameters.filename
            ),
            fileName: parameters.filename,
            fileExt: parameters.filename.getFileExtension ?? "pdf",
            errorType: NetworkRequestError.self
        ) { response in
            switch response {
            case .success(let data):
                completion(.success(data))
            case .failure(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            case .customError(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            }
        }
    }
    
    public func getCoverages(_ parameters: PolicyCoverageParameters,
                             completion: @escaping ((Result<[CoverageData], RequestError>) -> Void)) {
        network.request(
            endpoint: PoliciesModuleEndpoint.coverages(
                policyId: parameters.id,
                offerId: parameters.offerId,
                reimbursement: parameters.reimbursement
            ),
            method: .GET,
            responseType: [RemoteCoverageData].self,
            errorType: NetworkRequestError.self
        ) { response in
            ResponseHandler.handle(mapper: CoverageDataListMapper(), 
                                   response: response,
                                   completion: completion)
        }
    }
}
