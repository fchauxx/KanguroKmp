import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public class ProfileRepository: ProfileRepositoryProtocol {

    private let network: NetworkProtocol
    
    public init(network: NetworkProtocol) {
        self.network = network
    }
    
    public func updateProfile(parameters: ProfileParameters, 
                              completion: @escaping ((Result<Void, RequestError>) -> Void)) {

        network.request(endpoint: ProfileModuleEndpoint.profile,
                        method: .PUT,
                        parameters: RemoteProfileParameters(givenName: parameters.givenName,
                                                            surname: parameters.surname,
                                                            phone: parameters.phone),
                        errorType: NetworkRequestError.self) { response in
            switch response {
            case .success:
                completion(.success(()))
            case .failure(let error), .customError(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            }
        }
    }
}
