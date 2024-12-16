import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public class ExternalLinksRepository: ExternalLinksRepositoryProtocol {

    private let network: NetworkProtocol
    
    public init(network: NetworkProtocol) {
        self.network = network
    }
    
    public func redirectToPartnerWebsite(
        partnerName: String,
        parameters: UserIdParameters,
        completion: @escaping ((Result<URLRedirect, RequestError>) -> Void)
    ) {
        network.request(endpoint: ExternalLinksEndpoint.partner(partnerName: partnerName, userId: parameters.id),
                        method: .GET,
                        responseType: RemoteURLRedirect.self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: URLRedirectMapper(), 
                                   response: response,
                                   completion: completion)
        }
    }
}
