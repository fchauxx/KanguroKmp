import Combine
import Resolver
import KanguroSharedData
import KanguroNetworkDomain

@available(*, deprecated, message: "Code is legacy. Not used. Review strongly before using it. Or deletion is recommended.")
class ReferralCodeModule  {
    
    // MARK: - Dependencies
    @LazyInjected var network: NetworkProtocol
}

// MARK: - UserModuleProtocol
extension ReferralCodeModule: ReferralCodeModuleProtocol {
    
    func getReferralCode(parameters: String,
                         completion: @escaping ((RequestResponse<ReferralCodeParameters, NetworkRequestError>) -> Void)) {
        network.request(endpoint: ReferralCodeModuleEndpoint.code(code: parameters),
                        method: .GET,
                        responseType: ReferralCodeParameters.self,
                        errorType: NetworkRequestError.self) { response in
            completion(response)
        }
    }
}
