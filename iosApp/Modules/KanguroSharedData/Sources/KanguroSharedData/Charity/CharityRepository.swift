import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public class CharityRepository: CharityRepositoryProtocol {

    private let network: NetworkProtocol
    
    public init(network: NetworkProtocol) {
        self.network = network
    }
    
    public func getCharities(completion: @escaping ((Result<[DonationCause], RequestError>) -> Void)) {
        network.request(endpoint: CharityModuleEndpoint.charity,
                        method: .GET,
                        responseType: [RemoteDonationCause].self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: DonationCausesMapper(), 
                                   response: response,
                                   completion: completion)
        }
    }
}
