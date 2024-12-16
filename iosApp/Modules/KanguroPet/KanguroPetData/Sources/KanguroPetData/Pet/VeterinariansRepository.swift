import Foundation
import KanguroSharedDomain
import KanguroSharedData
import KanguroPetDomain
import KanguroNetworkDomain

public final class VeterinariansRepository: VeterinariansRepositoryProtocol {

    let network: NetworkProtocol

    public init(network: NetworkProtocol) {
        self.network = network
    }

    public func getVeterinarians(completion: @escaping ((Result<[Veterinarian], RequestError>) -> Void)) {
        network.request(endpoint: VeterinarianModuleEndpoint.getVeterinarians,
                        method: .GET,
                        responseType: [RemoteVeterinarian].self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: VeterinariansMapper(), 
                                   response: response,
                                   completion: completion)
        }
    }
}
