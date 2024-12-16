import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public class PasswordRepository: PasswordRepositoryProtocol {

    private let network: NetworkProtocol
    
    public init(network: NetworkProtocol) {
        self.network = network
    }
    
    public func updatePassword(parameters: PasswordParameters,
                               completion: @escaping ((Result<Void, RequestError>) -> Void)) {
        network.request(endpoint: PasswordModuleEndpoint.password,
                        method: .PUT,
                        parameters: RemotePasswordParameters(email: parameters.email,
                                                             currentPassword: parameters.currentPassword,
                                                             newPassword: parameters.newPassword),
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
