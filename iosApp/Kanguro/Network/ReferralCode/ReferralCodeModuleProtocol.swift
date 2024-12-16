import Foundation
import Combine
import KanguroSharedData
import KanguroNetworkDomain

@available(*, deprecated, message: "Code is legacy. Not used. Review strongly before using it. Or deletion is recommended.")
protocol ReferralCodeModuleProtocol {
    
    func getReferralCode(parameters: String,
                         completion: @escaping((RequestResponse<ReferralCodeParameters, NetworkRequestError>) -> Void))
}
