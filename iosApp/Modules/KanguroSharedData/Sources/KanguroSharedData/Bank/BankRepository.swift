import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public class BankRepository: BankRepositoryProtocol {

    private let network: NetworkProtocol
    
    public init(network: NetworkProtocol) {
        self.network = network
    }
    
    public func getBanks(completion: @escaping ((Result<[BankOption], RequestError>) -> Void)) {
        network.request(endpoint: BankModuleEndpoint.banks,
                        method: .GET,
                        responseType: [RemoteBankOption].self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: BankOptionsMapper(), 
                                   response: response,
                                   completion: completion)
        }
    }
}


