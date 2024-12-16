import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public class RemindersRepository: RemindersRepositoryProtocol {

    private let network: NetworkProtocol
    
    public init(network: NetworkProtocol) {
        self.network = network
    }

    public func getReminders(completion: @escaping ((Result<[Reminder], RequestError>) -> Void)) {
        network.request(endpoint: RemindersModuleEndpoint.reminder,
                        method: .GET,
                        responseType: [RemoteReminder].self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: RemindersMapper(), 
                                   response: response,
                                   completion: completion)
        }
    }
}
