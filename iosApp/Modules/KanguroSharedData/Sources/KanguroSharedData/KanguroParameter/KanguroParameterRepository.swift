import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public class KanguroParameterRepository: KanguroParameterRepositoryProtocol {

    private let network: NetworkProtocol
    
    public init(network: NetworkProtocol) {
        self.network = network
    }
    
    public func getInformationTopics(parameters: KanguroParameterModuleParameters,
                                     completion: @escaping ((Result<[InformerData], RequestError>) -> Void)) {

        let remoteKanguroParameter = RemoteKanguroParameterModuleParameters(key: parameters.key)
        network.request(endpoint: KanguroParametersModuleEndpoint.getInformationTopics(key: remoteKanguroParameter.key),
                        method: .GET,
                        responseType: [RemoteInformerData].self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: InformerDataListMapper(), 
                                   response: response,
                                   completion: completion)
        }
    }
}
