import Foundation

public protocol RedirectPartnerWebpageUseCaseProtocol {
    
    func execute(
        partnerName: String,
        parameters: UserIdParameters,
        completion: @escaping((Result<URLRedirect, RequestError>) -> Void)
    )
}
