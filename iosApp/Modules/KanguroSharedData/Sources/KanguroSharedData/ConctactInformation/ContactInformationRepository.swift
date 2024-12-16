import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public class ContactInformationRepository: ContactInformationRepositoryProtocol {

    private let network: NetworkProtocol

    public init(network: NetworkProtocol) {
        self.network = network
    }

    public func getContactInformation(completion: @escaping ((Result<[ContactInformation], RequestError>) -> Void)) {
        network.request(
            endpoint: ContactInformationEndpoint.getContactInformation,
            method: .GET,
            responseType: [RemoteContactInformation].self,
            errorType: NetworkRequestError.self
        ) { response in
            ResponseHandler.handle(mapper: ContactInformationMapper(),
                                   response: response,
                                   completion: completion)
        }
    }
}
