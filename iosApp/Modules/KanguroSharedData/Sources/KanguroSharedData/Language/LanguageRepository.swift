import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public class LanguageRepository: LanguageRepositoryProtocol {

    private let network: NetworkProtocol
    
    public init(network: NetworkProtocol) {
        self.network = network
    }
    
    public func updateAppLanguage(parameters: LanguageParameters, completion: @escaping ((Result<Void,RequestError>) -> Void)) {
        
        network.request(endpoint: LanguageModuleEndpoint.language,
                        method: .PUT,
                        parameters: RemoteLanguageParameters(language: parameters.language),
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
