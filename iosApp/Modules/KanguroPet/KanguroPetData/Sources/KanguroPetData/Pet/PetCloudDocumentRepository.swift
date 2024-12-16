import Foundation
import KanguroSharedDomain
import KanguroSharedData
import KanguroPetDomain
import KanguroNetworkDomain

public final class PetCloudDocumentRepository: CloudDocumentRepositoryProtocol {

    let network: NetworkProtocol

    public init(network: NetworkProtocol) {
        self.network = network
    }

    public func getCloudDocument(completion: @escaping ((Result<CloudDocument, RequestError>) -> Void)) {
        network.request(endpoint: CloudDocumentModuleEndpoint.CloudDocuments,
                        method: .GET,
                        responseType: RemotePetCloudDocument.self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: PetCloudDocumentMapper(), 
                                   response: response,
                                   completion: completion)
        }
    }

    public func getCloudDocument(by policyId: PolicyParameters, completion: @escaping ((Result<CloudDocumentPolicy, RequestError>) -> Void)) {
        network.request(
            endpoint: CloudDocumentModuleEndpoint.CloudDocumentsByPolicyId(
                policyId: policyId.id
            ),
            method: .GET,
            responseType: RemoteCloudDocumentPolicy.self,
            errorType: NetworkRequestError.self
        ) { response in
            ResponseHandler.handle(mapper: CloudDocumentPolicyMapper(), 
                                   response: response,
                                   completion: completion)
        }
    }

    public func getClaimDocument(by policyAndClaim: ClaimDocumentsParameters, completion: @escaping ((Result<ClaimDocument, RequestError>) -> Void)) {
        network.request(
            endpoint: CloudDocumentModuleEndpoint.ClaimDocumentsById(
                policyId: policyAndClaim.policyId,
                claimId: policyAndClaim.claimId
            ),
            method: .GET,
            responseType: RemoteClaimDocument.self,
            errorType: NetworkRequestError.self) { response in
                ResponseHandler.handle(mapper: ClaimDocumentMapper(), 
                                       response: response,
                                       completion: completion)
            }
    }
}
