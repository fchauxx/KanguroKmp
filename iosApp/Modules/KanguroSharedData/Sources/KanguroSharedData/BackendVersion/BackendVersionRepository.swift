import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public class BackendVersionRepository: BackendVersionRepositoryProtocol {

    private let network: NetworkProtocol
    
    public init(network: NetworkProtocol) {
        self.network = network
    }
    
    public func getBackendVersion(maxVersion: String, 
                                  completion: @escaping (Result<Void, BackendVersionError>) -> Void) {
        network.request(endpoint: BackendVersionModuleEndpoint.version,
                        method: .GET,
                        responseType: RemoteApiVersion.self,
                        errorType: NetworkRequestError.self) { response in
            switch response {
            case .failure, .customError:
                completion(.failure(.networkError))
            case .success(let backendVersion):
                guard maxVersion.apiVersionNumber >= backendVersion.version.apiVersionNumber else {
                    return completion(.failure(.invalidVersion))
                }
                completion(.success(()))
            }
        }
    }
}


