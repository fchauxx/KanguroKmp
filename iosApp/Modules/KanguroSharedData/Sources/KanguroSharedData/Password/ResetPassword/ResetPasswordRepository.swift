import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public class ResetPasswordRepository: ResetPasswordRepositoryProtocol {

    private let network: NetworkProtocol
    
    public init(network: NetworkProtocol) {
        self.network = network
    }
    
    public func createResetPassword(parameters: ResetPasswordParameters,
                                    completion: @escaping ((Result<Void, RequestError>) -> Void)) {
        network.request(endpoint: ResetPasswordModuleEndpoint.resetPassword,
                        method: .POST,
                        parameters: RemoteResetPasswordParameters(email: parameters.email),
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
